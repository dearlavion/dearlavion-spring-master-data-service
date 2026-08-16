# dearlavion-spring-master-data-service

The source of truth for Dear Lavion's reference data — the option lists behind the `/travel`
survey, product tagging, and the admin Kit Settings page. Nine built-in types ship with the
service, and admins can register further collections at runtime without a deploy.

## The two category axes (read this first)

The most common confusion in this service is `productCategory` vs `kitCategory`. They are not
redundant — they answer different questions:

| | **Product Category** | **Kit Category** |
| --- | --- | --- |
| Question | what a product **is** | what it's **packed in** |
| Cardinality | exactly one per product | several per product |
| Examples | Clothing, Electronics, Travel Accessories | Weather Kit, Toiletry Kit, Tech Kit |
| Drives | shop card label, search, related products | `/travel` survey question + scoring boost, My Kit grouping |

A rain jacket is **one Clothing product** that belongs in the **Weather Kit**. Nobody packs a
"clothing kit" — clothing distributes into purpose-built kits (a rain jacket → Weather, pyjamas →
Comfort, hiking socks → Activity Gear), which is why the kit list has no Clothing or Footwear entry
while the product list does.

## Collections

Every collection is described by a row in the `collections` registry (`GET /collections`), which is
what clients read to discover what exists and where to fetch it:

```json
{ "key": "destination", "label": "Destinations", "path": "destinations", "builtIn": true }
```

`path` is all a client needs: read at `/{path}`, write at `/admin/{path}`.

### Built-in (9)

Each has its own Mongo collection, Java class, and pair of controllers. Renameable, never deletable
— the survey and the kit-scoring engine reference their keys by name.

| Key | Label | Path | Mongo |
| --- | --- | --- | --- |
| `destination` | Destinations | `destinations` | `destinations` |
| `season` | Seasons | `seasons` | `seasons` |
| `party` | Parties | `parties` | `parties` |
| `transportation` | Transportation | `transportation-modes` | `transportation_modes` |
| `activity` | Activities | `activities` | `activities` |
| `kitCategory` | Kit Categories | `kit-categories` | `kit_categories` |
| `duration` | Durations | `durations` | `durations` |
| `gender` | Genders | `genders` | `genders` |
| `productCategory` | Product Categories | `product-categories` | `product_categories` |

`duration` is fixed at 4 values (`day`/`short`/`medium`/`long`, in a non-editable `code` field the
kit-sizing engine keys off). Admins may rename and reorder its rows; add and delete return 409.

### Admin-created

`POST /admin/collections` registers a new one at runtime. It has no Java class, so its values live
in **its own Mongo collection** named from the key (`fabricType` → `fabric_type`), created up front
with the same unique index on `value` the built-ins carry. Deleting the collection drops it.

A derived name that would collide with an existing collection's storage is rejected — a collection
keyed `destinations` would otherwise resolve onto the built-in Destination type's own data.

## Endpoints

| Method | Path | Auth | Notes |
| --- | --- | --- | --- |
| GET | `/collections` | public | the registry — built-in and custom alike |
| GET | `/{path}` | public | one collection's values, in `order` |
| POST | `/admin/{path}` | ROLE_ADMIN | |
| PUT/DELETE | `/admin/{path}/{id}` | ROLE_ADMIN | |
| POST | `/admin/collections` | ROLE_ADMIN | `{ "label": string, "key"?: string }` — key derived from the label when omitted |
| PUT | `/admin/collections/{key}` | ROLE_ADMIN | rename; works on built-ins too (only the key is immutable) |
| DELETE | `/admin/collections/{key}` | ROLE_ADMIN | 409 for built-ins; drops the values collection otherwise |
| GET | `/health` | public | |

Value body shape, identical for every collection:
`{ "value": string, "order"?: number, "emoji"?: string, "subtext"?: string }`.

> **Kit Settings is not here.** The survey's question order and each question's
> optional/required + single/multiple behaviour live in `store-engine-v2`
> (`GET /kit-settings`, `PUT /admin/kit-settings`) — that service owns the survey these settings
> configure. This service owns *what the options are*; it moved out of here along with the former
> `type_order` collection.

## Data model

Every value shares the same shape: `id`, `value`, `order`, `emoji?`, `subtext?`. `duration` adds
`code`, which is never writable through the API.

## Running locally

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
MONGODB_URI='mongodb+srv://…/master-data?…' ./mvnw spring-boot:run
```

| Var | Default | Notes |
| --- | --- | --- |
| `MONGODB_URI` | **required** | No localhost fallback: every environment runs against the shared Atlas cluster, and startup fails loudly rather than silently writing to a local Mongo nobody else can see. Not committed — this is a public repo. |
| `PORT` | `4012` | |
| `AUTH_SERVER_URL` | `http://localhost:9081` | `auth-service-v3` |
| `ADMIN_USERNAMES` | `admin` | bootstrap escape hatch; role is the primary admin signal |
| `FRONTEND_ORIGIN` | `http://localhost:4200` | CORS |

### Seeding (off by default)

Seeding overwrites live reference data, and this service points at the shared cluster, so the
`seed` profile alone isn't enough — both it **and** `app.seed-enabled` are required:

```bash
APP_SEED_ENABLED=true ./mvnw spring-boot:run -Dspring-boot.run.profiles=seed
```

`CollectionBootstrap` is separate and always runs: it inserts any missing built-in registry row and
backfills its storage name. Insert-only, so a renamed label survives every restart.

`CustomCollectionStorageMigration` is a one-time move from the old shared
`custom_collection_items` collection to per-collection storage. Its job is done and it drops the
legacy collection when it runs, so it's gated behind
`APP_CUSTOM_COLLECTION_MIGRATION_ENABLED=true`.

### Auth

JWTs are delegated to `auth-service-v3` (`AUTH_SERVER_URL`) via `POST /auth/verify`, the same trio
(`AuthClientService` / `AuthenticationFilter` / `SecurityConfig`) `payment-service-v2` uses.
`ROLE_ADMIN` is granted when the verified user's role is `ADMIN`/`STAFF`, or their username is in
`ADMIN_USERNAMES`.

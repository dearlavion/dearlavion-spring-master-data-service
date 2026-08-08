# dearlavion-spring-master-data-service

Standalone source-of-truth for Dear Lavion's reference data: `destination`, `season`, `party`,
`transportation`, `activity`, `kitCategory`, `duration`, `gender`. Built standalone alongside
`dearlavion-spring-store-engine-v2`'s existing `TaxonomyModule` — nothing else has been cut over
to this service yet; see "Scope" below.

## Scope

- Each type is its own Mongo collection + REST resource (not one generic axis-keyed collection).
- `duration` has a fixed set of 4 values (`day`/`short`/`medium`/`long` — the `code` field) —
  admin can rename/reorder existing rows but cannot add or delete any.
- `type-order` is a singleton document holding the admin-configurable display order of the 8
  types, mirroring store-engine-v2's `AxisOrder`.
- **Not done here**: retiring store-engine-v2's `TaxonomyModule`, or repointing the frontend's
  `TaxonomyService` at this service. That's a separate future cutover task.

## Endpoints

| Method | Path | Auth | Notes |
| --- | --- | --- | --- |
| GET | `/destinations` | public | |
| POST/PUT/DELETE | `/admin/destinations[/{id}]` | ROLE_ADMIN | |
| GET | `/seasons` | public | |
| POST/PUT/DELETE | `/admin/seasons[/{id}]` | ROLE_ADMIN | |
| GET | `/parties` | public | |
| POST/PUT/DELETE | `/admin/parties[/{id}]` | ROLE_ADMIN | |
| GET | `/transportation-modes` | public | |
| POST/PUT/DELETE | `/admin/transportation-modes[/{id}]` | ROLE_ADMIN | |
| GET | `/activities` | public | |
| POST/PUT/DELETE | `/admin/activities[/{id}]` | ROLE_ADMIN | |
| GET | `/kit-categories` | public | |
| POST/PUT/DELETE | `/admin/kit-categories[/{id}]` | ROLE_ADMIN | |
| GET | `/durations` | public | |
| POST | `/admin/durations` | ROLE_ADMIN | always 409 — fixed cardinality |
| PUT | `/admin/durations/{id}` | ROLE_ADMIN | rename/reorder/subtext only |
| DELETE | `/admin/durations/{id}` | ROLE_ADMIN | always 409 — fixed cardinality |
| GET | `/genders` | public | |
| POST/PUT/DELETE | `/admin/genders[/{id}]` | ROLE_ADMIN | |
| GET | `/type-order` | public | `{ "order": [...] }` |
| PUT | `/admin/type-order` | ROLE_ADMIN | `{ "order": [...] }` |

Create/update body shape (shared by all 8 types): `{ "value": string, "order"?: number, "emoji"?: string, "subtext"?: string }`.

## Data model

Every type shares the same base shape (`id`, `value`, `order`, `emoji?`, `subtext?`); `duration`
additionally carries `code` (never exposed via the API — only set by the seed data).

## Running locally

```
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
./mvnw spring-boot:run
```

Env vars (all optional, defaults shown):

| Var | Default |
| --- | --- |
| `PORT` | `4012` |
| `MONGODB_URI` | `mongodb://localhost:27017/dearlavion-spring-master-data-service-dev` |
| `AUTH_SERVER_URL` | `http://localhost:9081` |
| `ADMIN_USERNAMES` | `admin` |
| `FRONTEND_ORIGIN` | `http://localhost:4200` |

### Seed data

Populates all 8 types + default type order with the same values live in store-engine-v2's
`TaxonomySeedData`:

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=seed
```

### Auth

JWTs are delegated to `auth-service-v3` (`AUTH_SERVER_URL`) via `POST /auth/verify`, same trio
(`AuthClientService`/`AuthenticationFilter`/`SecurityConfig`) as `payment-service-v2`. `ROLE_ADMIN`
is granted when the verified user's role is `ADMIN`/`STAFF`, or their username is in
`ADMIN_USERNAMES`.

## Docker

```
docker build -t dearlavion-spring-master-data-service .
docker run -p 4012:4012 dearlavion-spring-master-data-service
```

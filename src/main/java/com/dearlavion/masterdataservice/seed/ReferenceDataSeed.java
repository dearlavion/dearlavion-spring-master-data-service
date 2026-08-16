package com.dearlavion.masterdataservice.seed;

import java.util.List;

/**
 * Byte-identical to store-engine-v2's TaxonomySeedData — same values, so this service's data
 * matches what's live today until an admin edits something.
 */
public final class ReferenceDataSeed {

    public record Entry(String type, String value, int order, String emoji, String subtext) {
        static Entry of(String type, String value, int order) {
            return new Entry(type, value, order, null, null);
        }

        static Entry of(String type, String value, int order, String emoji) {
            return new Entry(type, value, order, emoji, null);
        }
    }

    public record DurationEntry(String value, int order, String subtext, String code) {
    }

    public static final List<Entry> ENTRIES = List.of(
            Entry.of("destination", "Beach", 0, "🏖️"),
            Entry.of("destination", "Mountain", 1, "⛰️"),
            Entry.of("destination", "City", 2, "🏙️"),

            Entry.of("season", "Summer", 0, "☀️"),
            Entry.of("season", "Winter", 1, "❄️"),
            Entry.of("season", "Rainy", 2, "🌧️"),

            Entry.of("party", "Solo", 0),
            Entry.of("party", "Group", 1),

            Entry.of("transportation", "Flight", 0),
            Entry.of("transportation", "Car", 1),
            Entry.of("transportation", "Train", 2),
            Entry.of("transportation", "Cruise", 3),

            Entry.of("activity", "Hiking", 0),
            Entry.of("activity", "Swimming", 1),
            Entry.of("activity", "Sightseeing", 2),
            Entry.of("activity", "Business", 3),
            Entry.of("activity", "Photography", 4),
            Entry.of("activity", "Nightlife", 5),
            Entry.of("activity", "Food", 6),
            Entry.of("activity", "Relaxing", 7),

            Entry.of("kitCategory", "Essentials Kit", 0),
            Entry.of("kitCategory", "Toiletry Kit", 1),
            Entry.of("kitCategory", "Beauty & Grooming Kit", 2),
            Entry.of("kitCategory", "Health & Safety Kit", 3),
            Entry.of("kitCategory", "Comfort Kit", 4),
            Entry.of("kitCategory", "Tech Kit", 5),
            Entry.of("kitCategory", "Activity Gear Kit", 6),
            Entry.of("kitCategory", "Weather Kit", 7),
            Entry.of("kitCategory", "Laundry Kit", 8),
            Entry.of("kitCategory", "Kids & Baby Kit", 9),
            Entry.of("kitCategory", "Pet Travel Kit", 10),

            Entry.of("productCategory", "Toiletries", 0),
            Entry.of("productCategory", "Beauty & Grooming", 1),
            Entry.of("productCategory", "Clothing", 2),
            Entry.of("productCategory", "Footwear", 3),
            Entry.of("productCategory", "Electronics", 4),
            Entry.of("productCategory", "Health & Safety", 5),
            Entry.of("productCategory", "Travel Accessories", 6),
            Entry.of("productCategory", "Activity Gear", 7),
            Entry.of("productCategory", "Food & Hydration", 8),
            Entry.of("productCategory", "Travel Documents", 9),
            Entry.of("productCategory", "Kids & Baby", 10),
            Entry.of("productCategory", "Pet Travel", 11),
            Entry.of("productCategory", "Laundry & Care", 12),

            Entry.of("gender", "Woman", 0),
            Entry.of("gender", "Man", 1),
            Entry.of("gender", "Nonbinary", 2),
            Entry.of("gender", "Prefer not to say", 3)
    );

    public static final List<DurationEntry> DURATION_ENTRIES = List.of(
            new DurationEntry("Day Tour", 0, "1 day", "day"),
            new DurationEntry("Quick escape", 1, "2–4 days", "short"),
            new DurationEntry("A proper break", 2, "1–2 weeks", "medium"),
            new DurationEntry("Living it", 3, "3+ weeks", "long")
    );

    private ReferenceDataSeed() {
    }
}

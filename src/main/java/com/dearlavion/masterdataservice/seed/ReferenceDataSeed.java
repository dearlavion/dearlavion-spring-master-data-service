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

            Entry.of("kitCategory", "Essentials", 0),
            Entry.of("kitCategory", "Toiletries", 1),
            Entry.of("kitCategory", "Beauty", 2),
            Entry.of("kitCategory", "Clothing", 3),
            Entry.of("kitCategory", "Footwear", 4),
            Entry.of("kitCategory", "Electronics", 5),
            Entry.of("kitCategory", "Accessories", 6),
            Entry.of("kitCategory", "Health & Safety", 7),
            Entry.of("kitCategory", "Activity Gear", 8),
            Entry.of("kitCategory", "Travel Documents", 9),

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

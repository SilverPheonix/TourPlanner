package at.technikum.studentmanagementsystem2.helpers;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UUIDv7GeneratorTest {

    @Test
    void testUUIDv7Format() {
        UUID uuid = UUIDv7Generator.generateUUIDv7();

        // Check if UUID is not null
        assertNotNull(uuid, "UUID should not be null");

        // Check UUID format (8-4-4-4-12 hex digits)
        String uuidString = uuid.toString();
        assertTrue(uuidString.matches("^[0-9a-f]{8}-[0-9a-f]{4}-7[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$"),
                "UUID does not match UUIDv7 format");
    }

    @Test
    void testUUIDv7Version() {
        UUID uuid = UUIDv7Generator.generateUUIDv7();

        // Check if version is 7
        assertEquals(7, uuid.version(), "UUID version should be 7");
    }

    @Test
    void testUUIDv7Variant() {
        UUID uuid = UUIDv7Generator.generateUUIDv7();

        // Check for IETF variant (most common)
        assertEquals(2, uuid.variant(), "UUID variant should be 2 (IETF RFC 4122)");
    }

    @Test
    void testUUIDv7Uniqueness() {
        Set<UUID> uuidSet = new HashSet<>();
        int totalUUIDs = 100_000; // Generate 100k UUIDs for uniqueness test

        for (int i = 0; i < totalUUIDs; i++) {
            UUID uuid = UUIDv7Generator.generateUUIDv7();
            assertTrue(uuidSet.add(uuid), "Duplicate UUID found at iteration " + i);
        }

        assertEquals(totalUUIDs, uuidSet.size(), "Duplicate UUIDs detected");
    }

    @Test
    void testUUIDv7TimestampOrder() throws InterruptedException {
        UUID firstUUID = UUIDv7Generator.generateUUIDv7();
        Thread.sleep(1); // Ensure different timestamp
        UUID secondUUID = UUIDv7Generator.generateUUIDv7();

        // Compare most significant bits (which include timestamp)
        assertTrue(UUIDv7Generator.extractUUIDv7Timestamp(firstUUID) < UUIDv7Generator.extractUUIDv7Timestamp(secondUUID),
                "UUIDs are not in expected chronological order");
    }

    @Test
    void testMultipleUUIDsHaveIncreasingTimestamps() throws InterruptedException {
        UUID[] uuids = new UUID[10];

        for (int i = 0; i < uuids.length; i++) {
            uuids[i] = UUIDv7Generator.generateUUIDv7();
            Thread.sleep(1); // Ensure timestamp increases
        }

        for (int i = 0; i < uuids.length - 1; i++) {
            long timestamp1 = UUIDv7Generator.extractUUIDv7Timestamp(uuids[i]);
            long timestamp2 = UUIDv7Generator.extractUUIDv7Timestamp(uuids[i + 1]);


            assertTrue(timestamp1 < timestamp2,
                    "Timestamps are not in strictly increasing order");
        }
    }
}

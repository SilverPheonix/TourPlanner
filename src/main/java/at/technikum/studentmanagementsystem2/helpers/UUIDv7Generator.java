package at.technikum.studentmanagementsystem2.helpers;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UUIDv7Generator {
    // Atomic to ensure thread-safe, monotonically increasing timestamp
    private static final AtomicLong lastTimestamp = new AtomicLong(0);
    private static final AtomicLong sequence = new AtomicLong(0);

    public static UUID generateUUIDv7() {
        long currentTimeMillis = Instant.now().toEpochMilli();

        long timestamp;
        long seq;

        synchronized (UUIDv7Generator.class) {
            timestamp = Math.max(currentTimeMillis, lastTimestamp.get());
            if (timestamp == lastTimestamp.get()) {
                seq = sequence.incrementAndGet();
            } else {
                lastTimestamp.set(timestamp);
                sequence.set(0);
                seq = 0;
            }
        }

        // 48 bits for timestamp, shifted to align with UUIDv7 layout
        long mostSigBits = (timestamp << 16) & 0xFFFFFFFFFFFF0000L;

        // Set version 7 (0b0111) in the UUID version bits
        mostSigBits |= (0x7 << 12);

        // Least significant bits: 62 bits for randomness + sequence number
        long randomBits = (UUID.randomUUID().getLeastSignificantBits() & 0x3FFFFFFFFFFFFFFFL) | seq;

        // Set variant bits (IETF variant 0b10xxxxxx)
        long leastSigBits = randomBits | 0x8000000000000000L;

        return new UUID(mostSigBits, leastSigBits);
    }

    public static long extractUUIDv7Timestamp(UUID uuid) {
        if (uuid.version() != 7) {
            throw new UnsupportedOperationException("Not a UUIDv7");
        }
        // Extract the first 48 bits of the most significant bits
        return (uuid.getMostSignificantBits() >>> 16) & 0xFFFFFFFFFFFFL;
    }

}



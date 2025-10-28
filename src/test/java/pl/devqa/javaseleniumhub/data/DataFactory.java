package pl.devqa.javaseleniumhub.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Map.entry;

/**
 * Generator zróżnicowanych danych testowych do rejestracji użytkownika.
 */
public final class DataFactory {

    private DataFactory() {
    }

    // ====== RNG: losowość lub deterministyczna powtarzalność przez -DdataSeed=123 ======
    private static final ThreadLocal<Random> RNG = ThreadLocal.withInitial(() -> {
        String seedProp = System.getProperty("dataSeed"); // opcjonalnie: -DdataSeed=123
        return (seedProp != null && !seedProp.isBlank())
                ? new Random(Long.parseLong(seedProp))
                : ThreadLocalRandom.current();
    });

    private static Random rnd() {
        return RNG.get();
    }

    // ====== Pulki wartości (krótkie, ale dość zróżnicowane) ======
    private static final List<String> FIRST_NAMES = List.of(
            "Jan", "Anna", "Marek", "Ewa", "Kasia", "Ola", "Piotr", "Tomasz", "Julia", "Michal",
            "Adam", "Iga", "Pawel", "Zofia", "Kamil", "Agnieszka"
    );
    private static final List<String> LAST_NAMES = List.of(
            "Nowak", "Kowalski", "Wisniewska", "Wojcik", "Kaminska", "Lewandowski", "Zielinski",
            "Szymanska", "Dąbrowski", "Pawlak", "Mazur", "Kaczmarek"
    );
    private static final List<String> COMPANIES = List.of(
            "DevQA", "BlueSoft", "Green Labs", "TestMakers", "AutoQ", "SolidByte", "NextSprint"
    );
    private static final List<String> STREET_NAMES = List.of(
            "Dluga", "Krotka", "Lipowa", "Sloneczna", "Lesna", "Kosciuszki", "Polna", "Ogrodowa",
            "Szkolna", "Kwiatowa", "Akacjowa", "Brzozowa"
    );
    private static final List<String> STREET_SUFFIX = List.of("St", "Ave", "Rd", "Ln", "Way");
    private static final List<String> MONTHS = List.of(
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    );

    // Kraje zgodne z typowymi listami na tego typu serwisach demo
    private static final List<String> COUNTRIES = List.of(
            "Canada", "United States", "Australia", "India", "Israel", "New Zealand", "Singapore"
    );

    // Mapowanie stanów/miast/telefonów dla większego realizmu
    private static final Map<String, List<String>> STATES = Map.ofEntries(
            entry("Canada", List.of("Ontario", "Quebec", "Alberta")),
            entry("United States", List.of("California", "Texas", "New York")),
            entry("Australia", List.of("New South Wales", "Victoria", "Queensland")),
            entry("India", List.of("Karnataka", "Maharashtra", "Delhi")),
            entry("Israel", List.of("Central", "Haifa", "Jerusalem")),
            entry("New Zealand", List.of("Auckland", "Wellington", "Canterbury")),
            entry("Singapore", List.of("Singapore"))
    );

    private static final Map<String, List<String>> CITIES = Map.ofEntries(
            entry("Canada", List.of("Toronto", "Montreal", "Calgary")),
            entry("United States", List.of("Los Angeles", "Houston", "New York")),
            entry("Australia", List.of("Sydney", "Melbourne", "Brisbane")),
            entry("India", List.of("Bengaluru", "Mumbai", "New Delhi")),
            entry("Israel", List.of("Tel Aviv", "Haifa", "Jerusalem")),
            entry("New Zealand", List.of("Auckland", "Wellington", "Christchurch")),
            entry("Singapore", List.of("Singapore"))
    );

    private static final Map<String, String> PHONE_PREFIX = Map.ofEntries(
            entry("Canada", "+1"), entry("United States", "+1"), entry("Australia", "+61"),
            entry("India", "+91"), entry("Israel", "+972"), entry("New Zealand", "+64"),
            entry("Singapore", "+65")
    );

    // ====== API publiczne ======

    /**
     * Tworzy świeże, zróżnicowane dane użytkownika.
     */
    public static UserData newUser() {
        // Imię i nazwisko
        String first = pick(FIRST_NAMES);
        String last = pick(LAST_NAMES);

        // Unikalny znacznik czasu (czytelny) + losowy sufiks
        String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String nick = (first + "_" + last + "_" + randAlphaNum(4)).toLowerCase(Locale.ROOT);

        // Email (domena testowa)
        String email = nick.replace(' ', '_') + "@example.test";

        // Hasło spełniające podstawowe wymagania złożoności
        String password = strongPassword(8);

        // Firma i adres
        String company = pick(COMPANIES);
        String address1 = streetAddress();
        String address2 = "Apt " + (100 + rnd().nextInt(900));

        // Kraj, stan, miasto, ZIP, telefon
        String country = pick(COUNTRIES);
        String state = pickOrFallback(STATES.get(country), "State");
        String city = pickOrFallback(CITIES.get(country), "City");
        String zipcode = zipFor(country);
        String mobile = PHONE_PREFIX.getOrDefault(country, "+00") + randomDigits(8);

        // Data urodzenia
        String day = String.valueOf(1 + rnd().nextInt(28));             // 1-28
        String month = pick(MONTHS);
        String year = String.valueOf(1975 + rnd().nextInt(30));           // 1975-2004

        // Newsletter/Offers
        boolean newsletter = rnd().nextBoolean();
        boolean offers = rnd().nextBoolean();

        String displayName = first + " " + last;

        return new UserData(
                displayName, email, password,
                first, last, company,
                address1, address2,
                country, state, city, zipcode,
                mobile, day, month, year,
                newsletter, offers
        );
    }

    // ====== Pomocnicze generatory ======
    private static String pick(List<String> list) {
        return list.get(rnd().nextInt(list.size()));
    }

    private static String pickOrFallback(List<String> list, String fallback) {
        if (list == null || list.isEmpty()) return fallback;
        return pick(list);
    }

    private static String streetAddress() {
        int num = 1 + rnd().nextInt(9999);
        String street = pick(STREET_NAMES);
        String suf = pick(STREET_SUFFIX);
        return num + " " + street + " " + suf;
    }

    private static String randomDigits(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(rnd().nextInt(10));
        return sb.toString();
    }

    private static String randAlphaNum(int len) {
        final String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(chars.charAt(rnd().nextInt(chars.length())));
        return sb.toString();
    }

    /**
     * Generuje silne hasło: min. 1 duża, 1 mała, 1 cyfra, 1 znak specjalny.
     */
    private static String strongPassword(int len) {
        final String lower = "abcdefghijklmnopqrstuvwxyz";
        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String digits = "0123456789";
        final String special = "!@#$%^&*()-_+=";

        // minimalny szkielet spełniający wymagania
        String base = ""
                + lower.charAt(rnd().nextInt(lower.length()))
                + upper.charAt(rnd().nextInt(upper.length()))
                + digits.charAt(rnd().nextInt(digits.length()))
                + special.charAt(rnd().nextInt(special.length()));

        final String all = lower + upper + digits + special;
        StringBuilder tail = new StringBuilder();
        for (int i = 0; i < Math.max(0, len - base.length()); i++) {
            tail.append(all.charAt(rnd().nextInt(all.length())));
        }
        // prosta permutacja: wstawiamy bazę w losowe miejsce
        int pos = rnd().nextInt(tail.length() + 1);
        return tail.insert(pos, base).toString();
    }

    private static String zipFor(String country) {
        // nie komplikujemy formatów – wystarczy numeryczny ZIP 5-6 cyfr
        int len = switch (country) {
            case "United States", "Israel", "Singapore" -> 5;
            default -> 6;
        };
        return randomDigits(len);
    }

    /**
     * TC2: pobiera loginy z -DloginEmail/-DloginPassword lub używa wartości domyślnych.
     */
    public static Credentials existingUserTC2() {
        String email = System.getProperty("loginEmail", "TCLoginCorrecPass@o2.pl");
        String pass = System.getProperty("loginPassword", "j@WGYL45Br23jhw");
        return new Credentials(email, pass);
    }

    /**
     * TC3: zawsze nieistniejące lub błędne dane logowania.
     */
    public static Credentials invalidCredentials() {
        String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String email = ("invalid_" + stamp + "@example.test").toLowerCase(Locale.ROOT);
        // losowe „mocne” ale nieprawidłowe hasło
        String pass = "Wrong!" + ThreadLocalRandom.current().nextInt(100000, 999999);
        return new Credentials(email, pass);
    }
}

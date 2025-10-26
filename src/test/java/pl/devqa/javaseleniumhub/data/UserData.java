package pl.devqa.javaseleniumhub.data;

/** Dane rejestracyjne użytkownika. */
public record UserData(
        String name,
        String email,
        String password,
        String firstName,
        String lastName,
        String company,
        String address1,
        String address2,
        String country,
        String state,
        String city,
        String zipcode,
        String mobile,
        String day,
        String month,
        String year,
        boolean newsletter,
        boolean offers
) {}

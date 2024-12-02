# Email to Username Mapper for Keycloak

A Keycloak Identity Provider mapper that automatically generates usernames from email addresses during user federation/import.

## Description

This mapper is designed to work with Keycloak's OIDC identity providers. When a user logs in through an OIDC provider (like Google, Microsoft, etc.), it takes their email address and creates a unique username by:

1. Taking the local part of the email (before the @)
2. Appending a 4-character random hex string

For example:

- Email: `john.doe@example.com`
- Generated username: `john.doe1a2b`

## Features

- Compatible with Keycloak OIDC and Keycloak OIDC Identity Providers
- Automatic username generation from email addresses
- Supports all Identity Provider sync modes
- No configuration required
- Prevents username conflicts with random suffix

## Requirements

- Java 21
- Keycloak 26.0.6 or later
- Gradle (included wrapper)

## Installation

1. Build the JAR file:

   ```bash
   ./gradlew build shadowJar
   ```

2. Copy the generated JAR (`build/libs/email2username-mapper-1.0-SNAPSHOT.jar`) to Keycloak's providers directory:

   ```bash
   cp build/libs/email2username-mapper-1.0-SNAPSHOT.jar /path/to/keycloak/providers/
   ```

3. Restart Keycloak to load the new provider

## Usage

1. In Keycloak admin console, go to your realm's Identity Providers section
2. Select or create an OIDC-based Identity Provider
3. In the Identity Provider's configuration, go to the "Mappers" tab
4. Click "Add Mapper" and select "Email to Username" from the mapper type dropdown
5. Save the mapper configuration

The mapper will now automatically generate usernames for new users logging in through this Identity Provider.

## Building from Source

```bash
git clone https://github.com/yourusername/email2username-mapper.git
cd email2username-mapper
./gradlew build
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

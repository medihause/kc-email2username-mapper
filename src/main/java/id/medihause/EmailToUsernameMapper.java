package id.medihause;

import org.keycloak.broker.oidc.KeycloakOIDCIdentityProviderFactory;
import org.keycloak.broker.oidc.OIDCIdentityProviderFactory;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.provider.AbstractIdentityProviderMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.IdentityProviderSyncMode;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.broker.oidc.OIDCIdentityProvider;

import java.util.*;

/**
 * Maps email from any identity provider to username by stripping domain and adding random hex
 */
public class EmailToUsernameMapper extends AbstractIdentityProviderMapper {

    public static final String PROVIDER_ID = "email-to-username-mapper";
    public static final String[] COMPATIBLE_PROVIDERS = {"*"};

    private static final Set<IdentityProviderSyncMode> IDENTITY_PROVIDER_SYNC_MODES = new HashSet<>(Arrays.asList(IdentityProviderSyncMode.values()));

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String[] getCompatibleProviders() {
        return COMPATIBLE_PROVIDERS;
    }

    @Override
    public String getDisplayCategory() {
        return "Username Importer";
    }

    @Override
    public String getDisplayType() {
        return "Email to Username";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return new ArrayList<>();
    }

    @Override
    public String getHelpText() {
        return "Creates a username from email by stripping domain part and adding random hex characters. Works with any identity provider that supplies an email.";
    }

    @Override
    public void importNewUser(KeycloakSession session, RealmModel realm, UserModel user, IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        String email = context.getEmail();
        if (email != null && !email.isEmpty()) {
            try {
                String[] parts = email.split("@");
                if (parts.length > 0) {
                    String localPart = parts[0];
                    String randomHex = UUID.randomUUID().toString().substring(0, 4);
                    String newUsername = localPart + randomHex;
                    user.setUsername(newUsername);
                }
            } catch (Exception e) {
                // If there's any error in processing, log it but don't break the flow
                System.err.println("Error processing email for username: " + e.getMessage());
            }
        }
    }

    @Override
    public void updateBrokeredUser(KeycloakSession session, RealmModel realm, UserModel user, IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        // Username is already set, no need to update
    }

    @Override
    public boolean supportsSyncMode(IdentityProviderSyncMode syncMode) {
        return IDENTITY_PROVIDER_SYNC_MODES.contains(syncMode);
    }
}

package com.softwaholic.keycloak.auth;

import jakarta.ws.rs.core.MultivaluedMap;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.*;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.PasswordCredentialProvider;
import org.keycloak.credential.PasswordCredentialProviderFactory;

public class EmailTenantAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String email = formData.getFirst("email");
        String tenantId = formData.getFirst("tenant_id");
        String password = formData.getFirst("password");

        if (email == null || tenantId == null || password == null) {
            context.form()
                .setError("Missing email, tenant ID, or password");
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS);
            return;
        }

        String username = email + "__" + tenantId;

        UserModel user = context.getSession().users().getUserByUsername(context.getRealm(), username);
        if (user == null) {
            context.form().setError("User not found");
            context.failure(AuthenticationFlowError.INVALID_USER);
            return;
        }

        CredentialInput input_cred = UserCredentialModel.password(password);

        KeycloakSession session = context.getSession();
        PasswordCredentialProvider passwordProvider = (PasswordCredentialProvider)
                session.getProvider(CredentialProvider.class, PasswordCredentialProviderFactory.PROVIDER_ID);
        

        if (!passwordProvider.isValid(context.getRealm(), user, input_cred)) {
            context.form().setError("Invalid password");
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS);
            return;
        }

        context.setUser(user);
        context.success();
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String email = formData.getFirst("email");
        String tenantId = formData.getFirst("tenant_id");
        String password = formData.getFirst("password");

        if (email == null || tenantId == null || password == null) {
            context.form()
                .setError("Missing email, tenant ID, or password");
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS);
            return;
        }

        String username = email + "__" + tenantId;

        UserModel user = context.getSession().users().getUserByUsername(context.getRealm(), username);
        if (user == null) {
            context.form().setError("User not found");
            context.failure(AuthenticationFlowError.INVALID_USER);
            return;
        }

        CredentialInput input_cred = UserCredentialModel.password(password);

        KeycloakSession session = context.getSession();
        PasswordCredentialProvider passwordProvider = (PasswordCredentialProvider)
                session.getProvider(CredentialProvider.class, PasswordCredentialProviderFactory.PROVIDER_ID);
        

        if (!passwordProvider.isValid(context.getRealm(), user, input_cred)) {
            context.form().setError("Invalid password");
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS);
            return;
        }

        context.setUser(user);
        context.success();
    }

    @Override public void close() {}
    @Override public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}
    @Override public boolean requiresUser() { return false; }
    @Override public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) { return true; }
}

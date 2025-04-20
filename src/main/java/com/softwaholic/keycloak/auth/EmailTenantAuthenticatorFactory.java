package com.softwaholic.keycloak.auth;

import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class EmailTenantAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "email-tenant-authenticator";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Email and Tenant ID Authenticator";
    }// will be added in the keycloak UI when creating the flow

    @Override
    public Authenticator create(KeycloakSession session) {
        return new EmailTenantAuthenticator();
    } // It will create an authenticator instance
    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }


    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return List.of(); // return empty list for now
    }
    @Override public void init(Config.Scope config) {}
    @Override public void postInit(KeycloakSessionFactory factory) {}
    @Override public void close() {}
    @Override public boolean isConfigurable() { return true; }
    @Override public Requirement[] getRequirementChoices() {
        return new Requirement[] { Requirement.REQUIRED };
    }
    @Override public String getHelpText() {
        return "Authenticate using email + tenant ID + password.";
    }
    @Override public String getReferenceCategory() {
        return null;
    }
}

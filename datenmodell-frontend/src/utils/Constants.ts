// - Keycloak and session management related settings

export const KEYCLOAK_INIT_OPTIONS = {
  realm: 'dateninstitut',
  url: 'http://localhost:20010/',
  clientId: 'datenmodell-frontend',
};

export const TIME_DISTANCE_SET_INTERVAL_SESSION_CHECK_IN_MS = 5000;

export const TIME_BEFORE_REFRESH_TOKEN_EXPIRY_TO_DISPLAY_SESSION_WARNING_IN_MS = 2 * 60 * 1000;
// => This always has to be shorter than the ssoSessionIdleTimeout value in the realm settings.

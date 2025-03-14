<script setup lang="ts">
import { RouterView } from 'vue-router'
import AuthSection from "@/components/auth/AuthSection.vue";
import TheOverview from './components/TheOverview.vue'
</script>


<template>
  <div class="flex flex-col">
    <div class="p-4 bg-blue-100 justify-center"  >
      Platzhalter fuer NavBar ueber Router
    </div>
    <DynamicDialog />
    <RouterView />
    <TheOverview/>
    <AuthSection/>
  </div>
</template>


<script lang="ts">
import { computed, defineComponent, ref } from 'vue';
import DynamicDialog from 'primevue/dynamicdialog';
import Keycloak from 'keycloak-js';
import { logoutAndRedirectToUri } from '@/utils/KeycloakUtils';
import {
  SessionDialogMode,
  startSessionSetIntervalFunctionAndReturnItsId,
  updateTokenAndItsExpiryTimestampAndStoreBoth,
} from '@/utils/SessionTimeoutUtils';
import SessionDialog from '@/components/general/SessionDialog.vue';
import { KEYCLOAK_INIT_OPTIONS } from '@/utils/Constants';
import { useSharedSessionStateStore } from '@/stores/Stores';
import { ApiClientProvider } from '@/services/ApiClients';

export default defineComponent({
  name: 'app',
  components: { DynamicDialog, AuthSection },

  data() {
    return {
      keycloakPromise: undefined as undefined | Promise<Keycloak>,
      resolvedKeycloakPromise: undefined as undefined | Keycloak,
      keycloakAuthenticated: false,
      functionIdOfSessionSetInterval: undefined as number | undefined,
      apiClientProvider: undefined as ApiClientProvider | undefined,
    };
  },

  computed: {
    currentRefreshTokenInSharedStore() {
      return useSharedSessionStateStore().refreshToken;
    },
  },

  watch: {
    currentRefreshTokenInSharedStore(newRefreshToken: string) {
      if (this.resolvedKeycloakPromise && newRefreshToken) {
        this.resolvedKeycloakPromise.refreshToken = newRefreshToken;
        if (this.functionIdOfSessionSetInterval) {
          clearInterval(this.functionIdOfSessionSetInterval);
        }
        const openSessionWarningModalBound = this.openSessionWarningModal.bind(this);
        this.functionIdOfSessionSetInterval = startSessionSetIntervalFunctionAndReturnItsId(
          this.resolvedKeycloakPromise,
          openSessionWarningModalBound
        );
      }
    },
  },

  provide() {
    return {
      getKeycloakPromise: (): Promise<Keycloak> => {
        if (this.keycloakPromise) return this.keycloakPromise;
        throw new Error('The Keycloak promise has not yet been initialised. This should not be possible...');
      },
      authenticated: computed(() => {
        return this.keycloakAuthenticated;
      }),
      apiClientProvider: computed(() => {
        return this.apiClientProvider;
      }),
    };
  },

  created() {
    this.processUserAuthentication();
  },

  methods: {
    /**
     * Sets up the whole authentication status of the user when starting the Frontend App.
     */
    processUserAuthentication() {
      this.keycloakPromise = this.initKeycloak();
      if (this.keycloakPromise) {
        const apiClientProvider = new ApiClientProvider(this.keycloakPromise);
        this.apiClientProvider = apiClientProvider;
        this.keycloakPromise
        .then((keycloak) => this.handleResolvedKeycloakPromise(keycloak))
        .catch((e) => console.log(e));
      }
    },
    /**
     * Initializes the Keycloak adaptor and configures it according to the requirements of the application.
     * @returns a promise which resolves to the Keycloak adaptor object
     */
    initKeycloak(): Promise<Keycloak> {
      const keycloak = new Keycloak(KEYCLOAK_INIT_OPTIONS);
      keycloak.onAuthLogout = this.handleAuthLogout.bind(this);
      return keycloak
      .init({
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/static/silent-check-sso.html',
        pkceMethod: 'S256',
      })
      .then((authenticated) => {
        this.keycloakAuthenticated = authenticated;
      })
      .catch((error) => {
        console.log('Error in init Keycloak ', error);
        this.keycloakAuthenticated = false;
      })
      .then((): Keycloak => {
        return keycloak;
      });
    },

    /**
     * Executes actions based on the resolved Keycloak-login-status of the current user
     * @param resolvedKeycloakPromise contains the login-status of the current user
     */
    handleResolvedKeycloakPromise(resolvedKeycloakPromise: Keycloak) {
      this.resolvedKeycloakPromise = resolvedKeycloakPromise;
      if (this.resolvedKeycloakPromise.authenticated) {
        updateTokenAndItsExpiryTimestampAndStoreBoth(this.resolvedKeycloakPromise, true);
      }
    },

    /**
     * Executed as callback when the user is logged out. It tries another logout by redirecting the user to a keycloak
     * logout uri, where the user is instantly re-redirected back to the Welcome page with a specific query param
     * in the url which triggers a pop-up to open and inform the user that she/he was just logged out.
     */
    handleAuthLogout() {
      logoutAndRedirectToUri(this.resolvedKeycloakPromise as Keycloak, '?externalLogout=true');
    },

    /**
     * Opens a pop-up to warn the user that the session will expire soon and offers a button to refresh it.
     * If the refresh button is clicked soon enough, the session is refreshed.
     * Else the text changes and tells the user that the session was closed.
     */
    openSessionWarningModal(): void {
      this.$dialog.open(SessionDialog, {
        props: {
          modal: true,
          closable: false,
          closeOnEscape: false,
          showHeader: false,
        },
        data: {
          sessionDialogMode: SessionDialogMode.SessionWarning,
        },
      });
    },
  },
});
</script>

<style scoped>
* {
  font-family: "Roboto", sans-serif;
}
</style>

<template>
  <div>
    <Button
      v-if="isUserLoggedIn == false"
      icon="pi pi-user"
      label="Login"
      name="login_button"
      @click="login"
      class="login-button"
    />
    <Button
      v-if="isUserLoggedIn == true"
      icon="pi pi-user"
      label="Logout"
      name="logout_button"
      @click="logoutViaDropdown"
      class="login-button"
    />
  </div>
</template>

<script setup lang="ts">
import { inject, onMounted, ref } from 'vue';
import { assertDefined } from '@/utils/TypeScriptUtils';
import type Keycloak from 'keycloak-js';
import Button from 'primevue/button';
import { loginAndRedirectToSearchPage, logoutAndRedirectToUri } from '@/utils/KeycloakUtils';

const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
const isUserLoggedIn = ref<undefined | boolean>(undefined);
/**
 * Sends the user to the keycloak login page (if not authenticated already)
 */
const login = (): void => {
  assertDefined(getKeycloakPromise)()
    .then((keycloak) => {
      if (keycloak.authenticated) return;
      if (window.location.pathname == '/') {
        loginAndRedirectToSearchPage(keycloak);
      } else {
        keycloak.login().catch((error) => console.error(error));
      }
    })
    .catch((error) => console.error(error));
};
/**
 * Logs the user out and redirects him to the homepage
 */
const logoutViaDropdown = (): void => {
  assertDefined(getKeycloakPromise)()
    .then((keycloak) => {
      logoutAndRedirectToUri(keycloak, '');
    })
    .catch((error) => console.log(error));
};

onMounted(() => {
  assertDefined(getKeycloakPromise)()
    .then((keycloak) => {
      isUserLoggedIn.value = keycloak.authenticated;
    })
    .catch((error) => console.error(error));
});
</script>

<style scoped src="src/assets/main.css"></style>

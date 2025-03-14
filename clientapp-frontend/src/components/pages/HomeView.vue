<script setup lang="ts">

import { inject, ref } from 'vue'
import type Keycloak from "keycloak-js";
import { assertDefined } from "@/utils/TypeScriptUtils";

var getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
</script>


<template>
    Landing Page
</template>

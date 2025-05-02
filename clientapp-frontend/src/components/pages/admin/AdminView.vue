<script setup lang="ts">
import type Keycloak from 'keycloak-js';

import { inject, ref } from 'vue';
import { assertDefined } from '@/utils/TypeScriptUtils';
import { InputText } from 'primevue';

import { Button } from 'primevue';
import { Form } from '@primevue/forms';

import { postCreateProviderUser } from '@/services/ApiClients';
import { zodResolver } from '@primevue/forms/resolvers/zod';
import { useToast } from 'primevue/usetoast';
import { z } from 'zod';

const toast = useToast();
const initialValues = ref({
  username: '',
  institutionName: '',
  firstName: '',
  surname: '',
});

const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');

const postProviderUser = async (userName: string, firstName: string, surname: string) => {
  const response = await postCreateProviderUser(assertDefined(getKeycloakPromise), userName, firstName, surname);
  if (response == null) {
    toast.add({ severity: 'error', summary: 'Failed to create user', life: 3000 });
  } else {
    toast.add({ severity: 'success', summary: 'Created new user', life: 3000 });
  }
};

const resolver = ref(
  zodResolver(
    z.object({
      username: z.string().min(1, { message: 'Username is required.' }),
      institutionName: z.string().min(1, { message: 'Institution Name is required.' }),
    })
  )
);

const onFormCreateUser = ({ valid, states }) => {
  if (valid) {
    postProviderUser(states.userName.value, states.firstName.value, states.surname.value);
  }
};
</script>

<template>
  <div class="card flex justify-center gap-4">
    <Form
      :resolver="resolver"
      :initialValues="initialValues"
      @submit="onFormCreateUser"
      class="flex flex-col gap-4 w-full sm:w-56"
    >
      <div class="flex flex-col gap-1">
        <InputText name="userName" type="text" placeholder="Username" fluid />
      </div>
      <div class="flex flex-col gap-1">
        <InputText name="firstName" type="text" placeholder="First name" fluid />
      </div>
      <div class="flex flex-col gap-1">
        <InputText name="surname" type="text" placeholder="Surname" fluid />
      </div>
      <div>
        <Button type="submit" severity="secondary" label="Create User" />
      </div>
    </Form>
  </div>
</template>

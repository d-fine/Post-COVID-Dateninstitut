<script setup lang="ts">
import type Keycloak from 'keycloak-js';

import { useRoute } from 'vue-router';
import { inject, ref } from 'vue';

import { assertDefined } from '@/utils/TypeScriptUtils';
import { InputText, Button, Message, FloatLabel } from 'primevue';
import { useToast } from 'primevue/usetoast';
import { Form } from '@primevue/forms';
import { zodResolver } from '@primevue/forms/resolvers/zod';
import { z } from 'zod';

import { postTransactionUploadData } from '@/services/ApiClients';

const getKeycloakPromise = inject<() => Promise<Keycloak>>('getKeycloakPromise');
const route = useRoute();
const toast = useToast();
const initialValues = ref({
  clientId: '',
  transactionId: '',
  tableName: '',
});

const props = defineProps({
  datasetId: {
    type: String,
    default: null,
  },
});

const resolver = ref(
  zodResolver(
    z.object({
      clientId: z.string().min(1, { message: 'EuroDaT client ID is required' }),
      transactionId: z
        .string()
        .transform((value) => value.replace(/_/g, '-')) // Replace '_' with '-'
        .refine(
          (value) => {
            return z.string().uuid().safeParse(value).success;
          },
          { message: 'Invalid UUID format for EuroDaT transaction ID' }
        ),
      tableName: z.string().min(1, { message: 'table name is required' }),
    })
  )
);

const uploadData = async (clientId: string, transactionId: string, tableName: string) => {
  const response = await postTransactionUploadData(
    assertDefined(getKeycloakPromise),
    clientId,
    transactionId,
    tableName,
    props.datasetId ?? route.query.researchDataId
  );
  if (response == null) {
    toast.add({ severity: 'error', summary: 'Failed to upload data to EuroDaT', life: 3000 });
  } else {
    toast.add({ severity: 'success', summary: 'Uploaded dataset to EuroDaT', life: 3000 });
  }
};

const onFormUpload = ({ valid, states }) => {
  if (valid) {
    uploadData(states.clientId.value, states.transactionId.value, states.tableName.value);
  }
};
</script>

<template>
  <div class="card flex-col">
    <Form
      v-slot="$form"
      :resolver="resolver"
      :initialValues="initialValues"
      @submit="onFormUpload"
      class="flex flex-col gap-6 mt-8 mb-8 w-full sm:w-56"
    >
      <div class="flex flex-col gap-1 w-97">
        <FloatLabel>
          <InputText name="clientId" type="text" fluid />
          <label> EuroDat Client-ID</label>
        </FloatLabel>
        <Message v-if="$form.clientId?.invalid" severity="error" size="small" variant="simple">{{
          $form.tableName.error?.message
        }}</Message>
      </div>
      <div class="flex flex-col gap-1 w-97">
        <FloatLabel>
          <InputText name="transactionId" type="text" fluid />
          <label> EuroDaT Transaktions-ID </label>
        </FloatLabel>
        <Message v-if="$form.transactionId?.invalid" severity="error" size="small" variant="simple">{{
          $form.transactionId.error?.message
        }}</Message>
      </div>
      <div class="flex flex-col gap-1 w-97 mb-5">
        <FloatLabel>
          <InputText name="tableName" type="text" fluid />
          <label>EuroDaT Input-Tabelle</label>
        </FloatLabel>
        <Message v-if="$form.tableName?.invalid" severity="error" size="small" variant="simple">{{
          $form.tableName.error?.message
        }}</Message>
      </div>
      <div id="button_center">
        <Button type="submit" severity="secondary" label="DATEN BEREITSTELLEN" />
      </div>
    </Form>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>

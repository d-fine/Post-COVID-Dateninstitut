<script lang="ts">
import { Button, Menubar } from "primevue"
import DatennexusLogo from "./DatennexusLogo.vue"
import AuthSection from "./AuthSection.vue";

export default {
  data() {
    return {
      logo: "/datennexus_logo.svg",
      items: [
        { label: "Admin", route: "/admin"},
        { label: "Home", route: "/" },
        {
          label: "Daten", route: "/data/",
          items: [
            {
              route: "/data/explorer",
              label: "Bereitgestellt"
            },
            {
              route: "/data/new-upload",
              label: "Neuer Upload"
            }
          ]
        }
      ],
    }
  },
  methods: {
    login() {
      alert("Login not supported")
    },

    isActive(route) {
      const currentRoute = this.$route.path
      if (route === "/") {
        return currentRoute === route
      }
      return currentRoute.startsWith(route)
    },
  },
  computed: {
    SubNavBar() {
      const currentRoute = this.$route.path
      for (const route of this.items) {
        if ("items" in route) {
          for (const subroute of route.items!) {
            if (currentRoute.startsWith(`${subroute.route}`)) {
              return route
            }
          }
        }
      }
      return false
    },
  },

  components: {
    AuthSection,
    Button,
    DatennexusLogo,
    Menubar,
  },
}
</script>

<template>
  <div class="max-w-6xl pb-4 pt-4">
    <Menubar :model="items">
      <template #start>
        <router-link :to="'/'" class="no-underline">
          <DatennexusLogo />
        </router-link>
      </template>
      <template #item="{ item, props }">
        <a v-if="item.items" v-bind="props.action" @mouseover="">
          <span :class="{ active: isActive(item.route) }">
            {{ item.label }}</span>
          <span class="pi pi-fw pi-angle-down" />
        </a>
        <router-link v-else v-slot="{ navigate }" :to="item.route" custom>
          <a v-bind="props.action" @click="navigate">
            <span :class="{
              'text-(--df-orange)': isActive(item.route),
            }">
              {{ item.label }}
            </span>
          </a>
        </router-link>
      </template>
      <template #end>
        <AuthSection />
      </template>
    </Menubar>
    <div v-if="SubNavBar">
      <Menubar :model="SubNavBar.items" style="background-color: rgba(255, 255, 255, 0.7); padding: 1px">
        <template #item="{ item, props }">
          <router-link v-slot="{ navigate }" :to="item.route" custom>
            <a v-bind="props.action" @click="navigate">
              <span :class="{ active: isActive(item.route) }">
                {{ item.label }}
              </span>
            </a>
          </router-link>
        </template>
      </Menubar>
    </div>
  </div>
</template>

<style scoped src="src/assets/main.css"></style>

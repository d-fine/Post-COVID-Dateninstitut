<script lang="ts">
import { Button as PrimeButton, Menubar } from 'primevue';
import DatennexusLogo from './DatennexusLogo.vue';

export default {
  data() {
    return {
      logo: '/datennexus_logo.svg',
      items: [
        { label: 'Home', route: '/' },
        {
          label: 'Über uns',
          route: '/about',
          items: [
            {
              route: '/about/target',
              label: 'Das Zielbild',
            },
            {
              route: '/about/challenge',
              label: 'Die Challenge',
            },
            {
              route: '/about/team',
              label: 'Das Team',
            },
          ],
        },
        {
          label: 'Technik',
          route: '/tech',
          items: [
            {
              label: 'EuroDat',
              route: '/tech/eurodat',
            },
            {
              label: 'Gitlab',
              route: '/tech/gitlab',
            },
          ],
        },
        {
          label: 'ResearchHub',
          route: '/researchhub',
          items: [
            {
              label: 'Übersicht',
              route: '/researchhub/info',
            },
            {
              label: 'Data Science Marktplatz',
              route: '/researchhub/datascience',
            },
            {
              label: 'Daten Marktplatz',
              route: '/researchhub/markt',
            },
          ],
        },
        { label: 'Workshops', route: '/workshops' },
        {
          label: 'Datenkatalog',
          route: '/datacatalog',
          items: [
            {
              label: 'Übersicht',
              route: '/datacatalog/info',
            },
            {
              label: 'Datenkatalog',
              route: '/datacatalog/data',
            },
          ],
        },
        { label: 'FAQ', route: '/faq' },
      ],
    };
  },
  methods: {
    login() {
      alert('Login not supported');
    },

    isActive(route) {
      const currentRoute = this.$route.path;
      if (route === '/') {
        return currentRoute === route;
      }
      return currentRoute.startsWith(route);
    },
  },
  computed: {
    SubNavBar() {
      const currentRoute = this.$route.path;
      for (const route of this.items) {
        if ('items' in route) {
          for (const subroute of route.items!) {
            if (currentRoute.startsWith(`${subroute.route}`)) {
              return route;
            }
          }
        }
      }
      return false;
    },
  },

  components: {
    PrimeButton,
    DatennexusLogo,
    Menubar,
  },
};
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
        <a v-if="item.items" v-bind="props.action">
          <span :class="{ active: isActive(item.route) }"> {{ item.label }}</span>
          <span class="pi pi-fw pi-angle-down" />
        </a>
        <router-link v-else v-slot="{ navigate }" :to="item.route" custom>
          <a v-bind="props.action" @click="navigate">
            <span
              :class="{
                'text-(--df-orange)': isActive(item.route),
              }"
            >
              {{ item.label }}
            </span>
          </a>
        </router-link>
      </template>
      <template #end>
        <PrimeButton icon="pi pi-user" label="Login" @click="login" class="login-button" />
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

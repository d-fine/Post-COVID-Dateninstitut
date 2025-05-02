import pluginVue from 'eslint-plugin-vue';
import vueTsEslintConfig from '@vue/eslint-config-typescript';
import eslintConfigPrettier from 'eslint-config-prettier/flat';

export default [
  {
    name: 'app/files-to-lint',
    files: ['**/*.{ts,mts,tsx,vue}'],
  },

  {
    name: 'app/files-to-ignore',
    ignores: ['**/dist/**', '**/dist-ssr/**', '**/coverage/**'],
  },

  {
    rules: {
      'linebreak-style': ['error', 'windows'], // For CRLF
    },
  },

  ...pluginVue.configs['flat/essential'],
  ...vueTsEslintConfig(),
  eslintConfigPrettier,
];

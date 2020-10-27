const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
  purge: ['src/**/*.clj{s,c}'],
  theme: {
    height:{
      '3/4': "75vh"
    },
    extend: {
      fontFamily: {
        sans: ['Inter var', ...defaultTheme.fontFamily.sans]
      },
      backgroundImage: theme => ({
        'header-image': "url('/img/header-bg.jpg')",
        'stats-image': "url('/img/stats-bg.jpg')",
      })
    }
  },
  variants: {},
  plugins: [require('@tailwindcss/ui')]
}

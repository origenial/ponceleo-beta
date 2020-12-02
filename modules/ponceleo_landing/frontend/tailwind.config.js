const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
  purge: ['src/**/*.clj{s,c,}'],
  theme: {
    height:{
      '3/4': "75vh"
    },
    extend: {
      fontFamily: {
        sans: ['Inter var', ...defaultTheme.fontFamily.sans],
        cursive : ['"Brush Script MT"', '"Brush Script Std"',
                   '"Lucida Calligraphy"', '"Lucida Handwriting"',
                   '"Apple Chancery"', 'cursive'],
        fantasy : ['Papyrus', 'Herculanum', '"Party LET"', '"Curlz MT"',
                   'Harrington', 'fantazy']},
      backgroundImage: theme => ({
        'header-image': "url('/img/header-bg.webp')",
        'stats-image': "url('/img/stats-bg.webp')",
      })
    }
  },
  variants: {},
  plugins: [require('@tailwindcss/ui')]
}

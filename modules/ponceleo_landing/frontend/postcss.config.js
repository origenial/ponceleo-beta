const postcssConfig = {
  plugins: [
    require('postcss-import'),
    require('tailwindcss'),
    require('autoprefixer'),
    require('postcss-nested')
  ]
};

// If we are in production mode, then add cssnano
if (process.env.NODE_ENV === 'production') {
  postcssConfig.plugins.push(require('cssnano'));
}

module.exports = postcssConfig;

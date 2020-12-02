const postcssConfig = {
	plugins: {
		'postcss-import': {},
		'postcss-nested': {},
		'tailwindcss': {},
		'autoprefixer': {},
	}
}

// If we are in production mode, then add cssnano
if (process.env.NODE_ENV === 'production') {
	postcssConfig.plugins['cssnano']={};
}


module.exports = postcssConfig;

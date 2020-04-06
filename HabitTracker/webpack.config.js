var path = require('path');

module.exports = {
    entry: './scripts/src/main.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, './scripts/dist'),
        library: 'HabitTracker',
        libraryTarget: 'var'
    },
    module: {
        rules: [
            {
                test: /\.(js)$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                      presets: ['@babel/preset-env']
                    }
                }
            }
        ]
    }
}
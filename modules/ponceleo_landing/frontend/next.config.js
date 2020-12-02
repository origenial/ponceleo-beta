const { PHASE_DEVELOPMENT_SERVER } = require('next/constants');

module.exports = function dependingOnPhase(phase, {defaultConfig}){

    /** development only config options here **/
    if (phase === PHASE_DEVELOPMENT_SERVER) {
        return defaultConfig;
    }

    /** NON DEV config options **/
    return defaultConfig;

}


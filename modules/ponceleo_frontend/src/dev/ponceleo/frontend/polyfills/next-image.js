/** This file is a workaround to override Next.js v10 Image component
 * (namespace 'next/image') and to use a very basic react image component
 * when working in developement environement.
 * (Used by Shadow-cljs npm dependency custom resolver)
 */

import React from 'react';

export default function createImage(props){
    return React.createElement('img', {...props});
}
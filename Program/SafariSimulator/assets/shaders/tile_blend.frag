#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float u_time;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

float noise(vec2 p){
    vec2 i = floor(p);
    vec2 f = fract(p);
    float a = rand(i);
    float b = rand(i + vec2(1.0, 0.0));
    float c = rand(i + vec2(0.0, 1.0));
    float d = rand(i + vec2(1.0, 1.0));
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(a, b, u.x) + (c - a)* u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

void main() {
    vec4 baseColor = texture2D(u_texture, v_texCoords);

    float shimmer = sin(u_time * 2.0 + v_texCoords.x * 10.0) * 0.03;
    float blendNoise = noise(v_texCoords * 100.0 + u_time * 0.2);
    float blend = smoothstep(0.3, 0.7, blendNoise + shimmer);

    vec4 blendColor = vec4(0.8, 0.7, 0.4, 1.0); // sand blend

    gl_FragColor = mix(baseColor, blendColor, blend * 0.15);
}

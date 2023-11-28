#version 330 core

uniform sampler2D uTex0;
uniform sampler2D uTex1;
uniform sampler2D uTex2;
uniform sampler2D uTex3;
uniform sampler2D uTex4;
uniform sampler2D uTex5;
uniform sampler2D uTex6;
uniform sampler2D uTex7;

in vec4 fCol;
in vec2 fUv;
in float fTexId;

out vec4 fPixelCol;

void main() {
    switch (int(fTexId))
    {
        case -1:
            fPixelCol = fCol;
            break;
        case 0:
            fPixelCol = fCol * texture(uTex0, fUv);
            break;
        case 1:
            fPixelCol = fCol * texture(uTex1, fUv);
            break;
        case 2:
            fPixelCol = fCol * texture(uTex2, fUv);
            break;
        case 3:
            fPixelCol = fCol * texture(uTex3, fUv);
            break;
        case 4:
            fPixelCol = fCol * texture(uTex4, fUv);
            break;
        case 5:
            fPixelCol = fCol * texture(uTex5, fUv);
        break;
        case 6:
            fPixelCol = fCol * texture(uTex6, fUv);
            break;
        case 7:
            fPixelCol = fCol * texture(uTex7, fUv);
            break;
        default:
            fPixelCol = vec4(1.0, 1.0, 1.0, 1.0);
            break;
    }
}

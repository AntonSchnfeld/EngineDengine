#version 330 core

layout (location = 0) in vec3 vPos;
layout (location = 1) in vec4 vCol;

out vec4 fCol;

void main() {
    fCol = vCol;
    gl_Position = vec4(vPos.x, vPos.y, vPos.z, 1.0f);
}
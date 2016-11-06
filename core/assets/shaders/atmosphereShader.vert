
#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_uv;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_cameraPosition;
uniform vec4 u_lightPosition;

varying vec2 v_uv;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = normalize(vec4(a_normal.x, a_normal.y, a_normal.z, 0.0));
	normal = normalize(u_modelMatrix * normal);

	vec4 transformedOrigin = u_modelMatrix * vec4(0,0,0,1);

    vec4 s = normalize(u_lightPosition - position);

    //v_normal = normal;

    vec2 uv = a_uv;

    /**
    Calculate the dot product between the normal of the vertex in the atmosphere circle and the vector
    between the vertex and the sun. If both point in a similiar direction then the dot product should be higher than 0,
    if they point in opposite direction then the dot product should be less than 0.
    */
    float l = dot(s, normal);

    /**
    Calculate the dot product between the vector of the sun from origin and the camera from origin.
    If the camera is the same direction of origin as the sun then the atmosphere should be more bluish as the camera is facing the dayside of earth.
    The value c will be added to the value l to add up to the side of the earth which has a redish atmosphere but should have bluish atmosphere due
    to the position of the camera (The dot product between the normals and sun vector is close to 0 as they are almost perpindicular).
    */
    float c = dot(normalize(vec4(u_cameraPosition.x - transformedOrigin.x, u_cameraPosition.y - transformedOrigin.y,
                                 u_cameraPosition.z - transformedOrigin.z,0)),
                      normalize(vec4(u_lightPosition.x - transformedOrigin.x, u_lightPosition.y - transformedOrigin.y,
                                     u_lightPosition.z - transformedOrigin.z, 0)));
    /**
    clamp the c, and l+c values between 0 and 1, as those values are the min and max uv.x values
    */
    c = clamp(c, 0.0, 1.0);
    l = clamp(l + c, 0.0, 0.999);
    uv.x = l;           // Determine the x value of the atmosphere in the gradient texture (left is dark while right is blue).
    v_uv = uv;

    position = u_viewMatrix * position;
	gl_Position = u_projectionMatrix * position;
}
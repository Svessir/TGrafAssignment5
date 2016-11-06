package is.ru.graphics.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.Shader;

import java.nio.FloatBuffer;

/**
 * Created by Sverrir on 3.11.2016.
 */
public class PlanetAtmosphereGraphic {

    private static int verticesPerCircle = 100;
    private static FloatBuffer vertexBuffer;
    private static FloatBuffer normalBuffer;
    private static FloatBuffer uvBuffer;


    public static void create(float atmosphereHeight){
        float it = 0.0f;
        vertexBuffer = BufferUtils.newFloatBuffer(6*(verticesPerCircle + 1));
        normalBuffer = BufferUtils.newFloatBuffer(6*(verticesPerCircle + 1));
        uvBuffer = BufferUtils.newFloatBuffer(4*(verticesPerCircle + 1));

        Point3D[] firstTwo = new Point3D[2];
        Point3D lastVertex = null;
        int numberOfVertices = 2 * verticesPerCircle;

        for(int i = 0; i < numberOfVertices; i++){
            Point3D vertex;
            if(i % 2 == 0)
            {
                vertex = new Point3D((float)Math.cos(it),(float)Math.sin(it), 0);
                lastVertex = vertex;
                uvBuffer.put(0);
                uvBuffer.put(1);
            }
            else
            {
                vertex = new Point3D((float)Math.cos(it) * atmosphereHeight,(float)Math.sin(it) * atmosphereHeight, 0);
                Vector3D normal = new Vector3D
                        (
                            vertex.x - lastVertex.x,
                            vertex.y - lastVertex.y,
                            vertex.z - lastVertex.z
                        );

                // Insert the same normal for this vertex and previous vertex.
                for(int j = 0; j < 2; j++) {
                    normalBuffer.put(normal.x);
                    normalBuffer.put(normal.y);
                    normalBuffer.put(normal.z);
                }

                uvBuffer.put(0);
                uvBuffer.put(0);

                it += (2 * Math.PI) /(double) verticesPerCircle;
            }

            if(i < 2)
                firstTwo[i] = new Point3D(vertex.x, vertex.y, vertex.z);

            vertexBuffer.put(vertex.x);
            vertexBuffer.put(vertex.y);
            vertexBuffer.put(vertex.z);
        }

        for(int i = 0; i < firstTwo.length; i++) {
            vertexBuffer.put(firstTwo[i].x);
            vertexBuffer.put(firstTwo[i].y);
            vertexBuffer.put(firstTwo[i].z);
        }

        Vector3D normal = new Vector3D
                (
                        firstTwo[1].x - firstTwo[0].x,
                        firstTwo[1].y - firstTwo[0].y,
                        firstTwo[1].z - firstTwo[0].z
                );
        normalBuffer.put(normal.x);
        normalBuffer.put(normal.y);
        normalBuffer.put(normal.z);
        normalBuffer.put(normal.x);
        normalBuffer.put(normal.y);
        normalBuffer.put(normal.z);
        uvBuffer.put(0);
        uvBuffer.put(1);
        uvBuffer.put(0);
        uvBuffer.put(0);
        vertexBuffer.rewind();
        normalBuffer.rewind();
        uvBuffer.rewind();
    }

    public static void drawAtmosphere(Shader shader){
        Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
        Gdx.gl.glVertexAttribPointer(shader.getUvPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, 2*(verticesPerCircle + 1));

    }

}

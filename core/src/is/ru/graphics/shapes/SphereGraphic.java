package is.ru.graphics.shapes;

import java.nio.FloatBuffer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * Created by Sverrir on 16.10.2016.
 */
public class SphereGraphic {

    private static FloatBuffer vertexBuffer;
    private static FloatBuffer normalBuffer;
    private static FloatBuffer uvBuffer;
    private static int vertexPointer;
    private static int normalPointer;
    private static int verticesPerCircle = 50;


    private static int stacks = 50;
    private static int slices = 50;
    private static int vertexCount;

    public static void create(int vertexPointer, int normalPointer) {
        SphereGraphic.vertexPointer = vertexPointer;
        SphereGraphic.normalPointer = normalPointer;
        //VERTEX ARRAY IS FILLED HERE
        //float[] array = new float[2*verticesPerCircle];

        vertexCount = 0;
        float[] array = new float[(stacks)*(slices+1)*6];
        float[] uvArray = new float[(stacks) * (slices + 1) * 4];
        float stackInterval = (float)Math.PI / (float)stacks;
        float sliceInterval = 2.0f*(float)Math.PI / (float)slices;
        float stackAngle, sliceAngle;
        for(int stackCount = 0; stackCount < stacks; stackCount++)
        {
            stackAngle = stackCount * stackInterval;
            for(int sliceCount = 0; sliceCount < slices+1; sliceCount++)
            {
                sliceAngle = sliceCount * sliceInterval;
                array[vertexCount*3] = 	 (float)Math.sin(stackAngle) * (float)Math.cos(sliceAngle);
                array[vertexCount*3 + 1] = (float)Math.cos(stackAngle);
                array[vertexCount*3 + 2] = (float)Math.sin(stackAngle) * (float)Math.sin(sliceAngle);

                uvArray[vertexCount*2] = (float)(sliceCount) / (float)(slices);
                uvArray[vertexCount*2 + 1] = (float)(stackCount)/ (float)(stacks);

                array[vertexCount*3 + 3] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.cos(sliceAngle);
                array[vertexCount*3 + 4] = (float)Math.cos(stackAngle + stackInterval);
                array[vertexCount*3 + 5] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.sin(sliceAngle);

                uvArray[vertexCount*2 + 2] = (float)(sliceCount) / (float)(slices);
                uvArray[vertexCount*2 + 3] = (float)(stackCount + 1) / (float)(stacks);

                vertexCount += 2;
            }
        }
        vertexBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
        normalBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
        normalBuffer.put(array);
        normalBuffer.rewind();
        uvBuffer = BufferUtils.newFloatBuffer(vertexCount*2);
        uvBuffer.put(uvArray);
        uvBuffer.rewind();
    }

    public static void drawSolidSphere() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, normalBuffer);

        for(int i = 0; i < vertexCount; i += (slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, i, (slices+1)*2);
        }


    }

    public static void drawOutlineSphere() {

        Gdx.gl.glVertexAttribPointer(vertexPointer, 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glVertexAttribPointer(normalPointer, 3, GL20.GL_FLOAT, false, 0, normalBuffer);

        for(int i = 0; i < vertexCount; i += (slices+1)*2)
        {
            Gdx.gl.glDrawArrays(GL20.GL_LINE_STRIP, i, (slices+1)*2);
        }
    }

}

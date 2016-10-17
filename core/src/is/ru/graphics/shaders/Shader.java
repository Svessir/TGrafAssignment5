package is.ru.graphics.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.nio.FloatBuffer;

/**
 * Created by Sverrir on 17.10.2016.
 */
public abstract class Shader {

    protected int vertexShaderID;
    protected int fragmentShaderID;
    protected int renderingProgramID;
    protected int positionLoc;
    protected int normalLoc;
    protected int modelMatrixLoc;
    protected int viewMatrixLoc;
    protected int projectionMatrixLoc;
    protected int colorLoc;

    public Shader(String vertexShaderFileName, String fragmentShaderFileName)
    {
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal(vertexShaderFileName).readString();
        fragmentShaderString =  Gdx.files.internal(fragmentShaderFileName).readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        System.out.println("Vertex shader compile messages:");
        System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
        System.out.println("Fragment shader compile messages:");
        System.out.println(Gdx.gl.glGetShaderInfoLog(fragmentShaderID));

        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);

        positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(positionLoc);

        normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
        Gdx.gl.glEnableVertexAttribArray(normalLoc);

        colorLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

        modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
        viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        Gdx.gl.glUseProgram(renderingProgramID);
    }

    public void setColor(float r, float g, float b, float a) {
        Gdx.gl.glUniform4f(colorLoc, r, g, b, a);
    }

    public void setModelMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
    }

    public void setViewMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
    }

    public void setProjectionMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
    }

    public int getVertexPointer() {
        return positionLoc;
    }

    public int getNormalPointer() {
        return normalLoc;
    }
}

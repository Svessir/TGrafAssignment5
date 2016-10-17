package is.ru.graphics.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.nio.FloatBuffer;

/**
 * Created by Sverrir on 17.10.2016.
 */
public abstract class AbstractShader implements Shader {

    protected int renderingProgramID;
    protected int vertexShaderID;
    protected int fragmentShaderID;

    protected int positionLoc;
    protected int normalLoc;
    protected int uvLoc;

    protected int modelMatrixLoc;
    protected int viewMatrixLoc;
    protected int projectionMatrixLoc;

    protected int colorLoc;

    protected boolean usesDiffuseTexture = false;
    protected boolean usesAlphaTexture = false;
    protected boolean usesBumpTexture = false;
    protected boolean usesSpecularTexture = false;

    protected int usesDiffuseTexLoc;
    protected int diffuseTextureLoc;
    protected int usesAlphaTexLoc;
    protected int alphaTextureLoc;
    protected int usesBumpTexLoc;
    protected int bumpTextureLoc;
    protected int usesSpecularTexLoc;
    protected int specularTextureLoc;

    public AbstractShader(String vertexShader, String fragmentShader){
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal(vertexShader).readString();
        fragmentShaderString =  Gdx.files.internal(fragmentShader).readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
        Gdx.gl.glCompileShader(fragmentShaderID);
        System.out.println(Gdx.gl.glGetShaderInfoLog(fragmentShaderID));

        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);

        positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(positionLoc);

        normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
        Gdx.gl.glEnableVertexAttribArray(normalLoc);

        uvLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_uv");
        Gdx.gl.glEnableVertexAttribArray(uvLoc);

        usesDiffuseTexLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesDiffuseTexture");
        diffuseTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_diffuseTexture");
        usesAlphaTexLoc		    = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesAlphaTexture");
        alphaTextureLoc		    = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_alphaTexture");
        usesBumpTexLoc		    = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesBumpTexture");
        bumpTextureLoc		    = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_bumpTexture");
        usesSpecularTexLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesSpecularTexture");
        specularTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_specularTexture");

        Gdx.gl.glUniform1f(usesDiffuseTexLoc, 0.0f);
        Gdx.gl.glUniform1f(usesAlphaTexLoc, 0.0f);
        Gdx.gl.glUniform1f(usesBumpTexLoc, 0.0f);
        Gdx.gl.glUniform1f(usesSpecularTexLoc, 0.0f);

        modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
        viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        colorLoc                = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

        Gdx.gl.glUseProgram(renderingProgramID);
    }

    @Override
    public void setColor (float r, float g, float b, float a) { Gdx.gl.glUniform4f(colorLoc, r, g, b, a);}

    @Override
    public int getVertexPointer(){
        return  positionLoc;
    }

    @Override
    public int getNormalPointer(){
        return  normalLoc;
    }

    @Override
    public int getUvPointer() {
        return uvLoc;
    }

    @Override
    public void setModelMatrix(FloatBuffer matrix){
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
    }

    @Override
    public void setViewMatrix(FloatBuffer matrix){
        Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
    }

    @Override
    public boolean usesTexture() {
        return usesDiffuseTexture || usesAlphaTexture || usesBumpTexture || usesSpecularTexture;
    }

    @Override
    public void setDiffuseTexture(Texture tex) {
        usesDiffuseTexture = tex != null ? true : false;
        bindTexture(tex, 0, diffuseTextureLoc, usesDiffuseTexLoc);
    }

    @Override
    public void setAlphaTexture(Texture tex) {
        usesAlphaTexture = tex != null ? true : false;
        bindTexture(tex, 1, alphaTextureLoc, usesAlphaTexLoc);
    }

    @Override
    public void setBumpMapTexture(Texture tex) {
        usesBumpTexture = tex != null ? true : false;
        bindTexture(tex, 2, bumpTextureLoc, usesBumpTexLoc);
    }

    @Override
    public void setSpecularMapTexture(Texture tex) {
        usesSpecularTexture = tex != null ? true : false;
        bindTexture(tex, 3, specularTextureLoc, usesSpecularTexLoc);
    }

    @Override
    public void setProjectionMatrix(FloatBuffer matrix){ Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix); }

    protected void bindTexture(Texture tex, int layer, int textureLoc, int usesTextureLoc) {
        if(tex == null)
        {
            Gdx.gl.glUniform1f(usesTextureLoc, 0.0f);
        }
        else
        {
            tex.bind(layer);
            Gdx.gl.glUniform1i(textureLoc, layer);
            Gdx.gl.glUniform1f(usesTextureLoc, 1.0f);

            Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
            Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        }
    }
}

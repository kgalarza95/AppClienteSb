package vera.galarza.appclientesb.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import vera.galarza.appclientesb.util.UtilGlobal;

public class RepositoryApiRest {
    //static String URL = "http://192.168.1.48:8084/SBApiJdk8-0.1/";
    static String URL = "http://192.168.1.48:8084/";
    private Context context;
    //http://localhost:8084/SBApiJdk8-0.1/login/?user=user&password=password

    public RepositoryApiRest(Context context) {
        this.context = context;
    }

    public void apiLogin(final Map<String, String> params, final DAOCallbackServicio callback) {
        String usuario = params.get("usuario");
        String password = params.get("contrase");
        String URI = "login/?user="+usuario+"&password="+password;
        clienteRest(URL + URI, params, callback);
    }

    public void crudUsuario(final Map<String, String> params, final DAOCallbackServicio callback,  final int method) {
        String URI = "usuarios";
        clienteRestTokenBodyJson(URL + URI, params, callback, method);
    }


    public void getUsuario(final String id, final DAOCallbackServicio callback,  final int method) {
        Map<String, String> params = new HashMap<>();
        params.put("nombres", "");
        String URI = "usuarios/"+id;
        clienteRestTokenBodyJson(URL + URI, params, callback, method);
    }

    private void clienteRest(String URL,
                             final Map<String, String> params,
                             final DAOCallbackServicio callback) {
        Log.i("URL:        ", URL);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta aquí
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Procesar el error aquí
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void clienteRestToken(String URL, final Map<String, String> params,
                                  final DAOCallbackServicio callback,
                                  final int method) {
        Log.i("URL: ", URL);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(method, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta aquí
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Procesar el error aquí
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", UtilGlobal.JWT);
               // headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(stringRequest);
    }


    private void clienteRestTokenBodyJson(String URL, final Map<String, String> params,
                                  final DAOCallbackServicio callback,
                                  final int method) {
        Log.i("URL: ", URL);
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta aquí
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Procesar el error aquí
                        callback.onError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", UtilGlobal.JWT);
                // headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }


    private void clienteRestUTF8(String URL,
                                 final Map<String, String> params,
                                 final DAOCallbackServicio callback) {
        try {
            Log.i("URL:        ", URL);
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Procesar la respuesta aquí
                            callback.onSuccess(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Procesar el error aquí
                            callback.onError(error);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    try {
                        //Log.i("conversión-original", new String(response.data));
                        // Obtiene la cadena de bytes de la respuesta HTTP
                        String charset = HttpHeaderParser.parseCharset(response.headers, "UTF-8");
                        String jsonString = new String(response.data, charset);
                        //Log.i("conversión-convert", jsonString);
                        // Devuelve la cadena de caracteres utilizando la codificación adecuada
                        return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        // Manejo de errores
                        Log.e(TAG, "Error de codificación de caracteres", e);
                        return Response.error(new ParseError(e));
                    }
                }

            };

            queue.add(stringRequest);
        } catch (Exception e) {
            Log.e("Error en cliente rest ", e.toString());
        }

    }


    //interfaz interna para utilizar los métodos con la data.
    public interface DAOCallbackServicio {
        void onSuccess(String response);

        void onError(VolleyError error);
    }
}

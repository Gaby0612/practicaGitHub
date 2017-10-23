package abb;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JOptionPane;

public class ArbolBinarioBusqueda {

    Nodo raiz;
    Nodo aux1;
    Nodo anterior;
    Connection con = null;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

     public boolean VerificarCedula(String cedula) {
        int total = 0;
        int tamanoLongitudCedula = 10;
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int numeroProvincias = 24;
        int tercerDigito = 6;
        if (cedula.matches("[0-9]*") && cedula.length() == tamanoLongitudCedula) {
            int provincia = Integer.parseInt(cedula.charAt(0) + "" + cedula.charAt(1));
            int digitoTres = Integer.parseInt(cedula.charAt(2) + "");
            if ((provincia > 0 && provincia <= numeroProvincias) && digitoTres < tercerDigito) {
                int digitoVerificadorRecibido = Integer.parseInt(cedula.charAt(9) + "");
                for (int i = 0; i < coeficientes.length; i++) {
                    int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(cedula.charAt(i) + "");
                    total = valor >= 10 ? total + (valor - 9) : total + valor;
                }
                int digitoVerificadorObtenido = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10) : total;
                if (digitoVerificadorObtenido == digitoVerificadorRecibido) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    
    
    public Connection conexion() {
        try {
            //cargar nuestro driver
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/proyecto1_edii", "root", "Santy-Lo");
            JOptionPane.showMessageDialog(null, "conexion establecida");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error de conexion");
            JOptionPane.showMessageDialog(null, "error de conexion " + e);
        }
        return con;
    }

    public String ingresar(Object elemento) {
        String mensaje = "";
        Nodo nodo = new Nodo(elemento);
        int comp;
        if (raiz == null) {
            raiz = nodo;
            mensaje += "PERSONA INGRESADA CORRECTAMENTE";
        } else {
            Nodo aux = raiz;
            PersonaVotante pv = (PersonaVotante) nodo.getInfo();
            while (aux != null) {
                PersonaVotante pvArbol = (PersonaVotante) aux.getInfo();
                comp = pv.getCedula().compareTo(pvArbol.getCedula());

                if (comp > 0) {
                    if (aux.getDer() == null) {
                        aux.setDer(nodo);
                        mensaje = " Se ingreso a la derecha del arbol";
                        break;
                    } else {
                        aux = aux.getDer();
                    }
                }
                if (comp < 0) {
                    if (aux.getIzq() == null) {
                        aux.setIzq(nodo);
                        mensaje = " Se ingreso a la izquierda del arbol";
                        break;
                    } else {
                        aux = aux.getIzq();
                    }
                }
                if (comp == 0) {
                    mensaje = "El nodo ya existe";
                    break;
                }
            }
        }
        return mensaje;
    }
    
     public String arbolrecorridoanch(Nodo a) throws InterruptedException {
        String mensaje = "";
        a = raiz;
        Queue<Nodo> cola;
        Queue<Nodo> colaAux;
        Nodo aux = null;
        if (a != null) {
            cola = new LinkedList<>();
            colaAux = new LinkedList<>();
            cola.add(a);
            while (!cola.isEmpty()) {
                aux = cola.remove();
                colaAux.remove(aux);
                if (aux.getIzq() != null) {
                    cola.add(aux.getIzq());
                }
                if (aux.getDer() != null) {
                    cola.add(aux.getDer());
                }
                PersonaVotante per = (PersonaVotante) aux.getInfo();
                mensaje += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";

            }
        }
        return mensaje;
    }

    public String postorder(Nodo r) {
        String mensaje = "";
        if (r != null) {
            mensaje += postorder(r.getIzq());
            mensaje += postorder(r.getDer());
            PersonaVotante per = (PersonaVotante) r.getInfo();
            mensaje += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
        }
        return mensaje;

    }

    public String PreOrden(Nodo r) {
        String mensaje = "";
        if (r != null) {
            PersonaVotante per = (PersonaVotante) r.getInfo();
            mensaje += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
            mensaje += PreOrden(r.getIzq());
            mensaje += PreOrden(r.getDer());
        }
        return mensaje;

    }

    public String InOrden(Nodo r) {
        String mensaje = "";
        if (r != null) {
            mensaje += InOrden(r.getIzq());
            PersonaVotante per = (PersonaVotante) r.getInfo();
            mensaje += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
            mensaje += InOrden(r.getDer());
        }
        return mensaje;
    }
    public String InOrderNoRecursivo(Nodo raiz) {
        Nodo nodoActual = raiz;
        Stack<Nodo> pila = new Stack<>();
        int fin = 1;
        int r = 0;
        String res = "";
        while (r != fin) {
            while (nodoActual != null) {
                pila.push(nodoActual);
                nodoActual = nodoActual.getIzq();
            }
            if (!pila.empty()) {
                nodoActual = pila.pop();
                PersonaVotante per = (PersonaVotante) nodoActual.getInfo();
                res += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                nodoActual = nodoActual.getDer();
            } else {
                r = 1;
            }
        }
        return res;
    }

    public String PreOrderNoRecursivo(Nodo raiz) {
        Nodo aux = raiz;
        Stack<Nodo> pila = new Stack<>();
        //int fin = 1;
        int r = 0;
        String resp = "";
        while (r != 1) {
            while (aux != null) {
                if (aux.getDer() != null) {
                    pila.push(aux.getDer());
                }
                if (aux.getIzq() != null) {
                    PersonaVotante per = (PersonaVotante) aux.getInfo();
                    resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                    System.out.println(resp);
                    aux = aux.getIzq();
                } else {
                    PersonaVotante per = (PersonaVotante) aux.getInfo();
                    resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                    System.out.println(resp);
                    aux = pila.pop();
                    if (pila.empty()) {
                        if (aux.getIzq() == null && aux.getDer() == null) {
                            per = (PersonaVotante) aux.getInfo();
                            resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                            r = 1;
                            break;
                        }
                    }
                }
            }
        }
        return resp;
    }

    public String PosOrderNoRecursivo(Nodo raiz) {
        Nodo aux = raiz;
        String resp = "";
        Stack<PersonaVotante> a = new Stack<>();
        Stack<Nodo> der = new Stack<>();
        int r = 0;
        int s = 0;
        boolean bandera = false;
        while (r != 1) {
            if (bandera == true) {
                while (!a.empty()) {
                    PersonaVotante per = a.pop();
                    resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                }
                r = 1;
                aux = null;
            }
            while (aux != null) {
                if (bandera == true) {
                    for (int i = 1; i <= s; i++) {
                        PersonaVotante per = a.pop();
                        resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                    }
                    s = 0;
                    bandera = false;
                }
                if (aux.getDer() != null) {
                    der.push(aux.getDer());
                }
                if (aux.getIzq() != null) {
                    a.push((PersonaVotante) aux.getInfo());
                    aux = aux.getIzq();
                    s++;
                } else {
                    if (aux.getIzq() == null && aux.getDer() == null) {
                        if (der.empty()) {
                            PersonaVotante per = (PersonaVotante) aux.getInfo();
                            resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                            bandera = true;
                            break;
                        }
                        PersonaVotante per = (PersonaVotante) aux.getInfo();
                        resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                        aux = der.pop();
                        if (aux.getIzq() == null && aux.getDer() == null) {
                            bandera = true;
                            per = (PersonaVotante) aux.getInfo();
                            resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                            aux = der.pop();
                        }
                    } else {
                        Nodo aux2 = aux;
                        aux = der.pop();
                        if (aux2.getIzq() == null && aux2.getDer() != null && aux.getDer() == null && aux.getIzq() == null) {
                            PersonaVotante per = (PersonaVotante) aux.getInfo();
                            resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                            per = (PersonaVotante) aux2.getInfo();
                            resp += per.getCedula() + "\t" + per.getNombre() + "\t" + per.getCodProvincia() + "\t" + per.getCodCanton() + "\t" + per.getCodParroquia() + "\t" + per.getRecinto() + "\t" + per.getMesa() + "\t" + per.getFechaNacimineto() + "\t" + per.getGenero() + "\n";
                            aux = der.pop();
                        } else {
                            if (aux2.getIzq() == null && aux2.getDer() != null) {
                                aux = aux2;
                                der.push(aux.getDer());
                                a.push((PersonaVotante) aux.getInfo());
                                aux = der.pop();
                                if (aux.getIzq() == null && aux.getDer() == null) {
                                    bandera = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return resp;
    }
    
     public String buscar(String dato) {
        //  nodo nuevo = new nodo(elem,null,null);
        Nodo aux;

        PersonaVotante persona2;
        int r;
        String res = " ";

        if (getRaiz() == null) {

            res = "No existen datos en el arbol ";
        } else {
            aux = getRaiz();
            //   pIngreso = (persona) elem;
            while (aux != null) {

                persona2 = (PersonaVotante) aux.getInfo();
                r = dato.compareTo(persona2.getCedula());
                if (r < 0) {
                    aux = aux.getIzq();
                } else if (r > 0) {
                    aux = aux.getDer();
                } else {
                    res = persona2.getCedula();
                    break;
                }

            }
        }

        return res;
    }

    public Nodo buscarNodo(String d) {
        Nodo aux = raiz;
        PersonaVotante per;
        if (raiz == null) {
            return null;
        } else {
            per = (PersonaVotante) aux.getInfo();

            while (aux != null) {

                if (d.compareTo(per.getCedula()) < 0) {
                    aux = aux.getIzq();
                    if (aux != null) {
                        per = (PersonaVotante) aux.getInfo();
                    }
                } else {
                    if (d.compareTo(per.getCedula()) == 0) {
                        return aux;
                    }

                    if (d.compareTo(per.getCedula()) > 0) {
                        aux = aux.getDer();
                        if (aux != null) {
                            per = (PersonaVotante) aux.getInfo();
                        }
                    }
                }
            }
        }
        return aux;
    }

    public Nodo buscarNodoPadre(String d) {
        Nodo aux = raiz;
        Nodo ant = null;
        Nodo result = null;
        PersonaVotante per;
        if (raiz == null) {
            return result;
        } else {
            per = (PersonaVotante) aux.getInfo();

            while (aux != null) {

                if (d.compareTo(per.getCedula()) < 0) {
                    ant = aux;
                    aux = aux.getIzq();
                    if (aux != null) {
                        per = (PersonaVotante) aux.getInfo();
                    }
                } else {
                    if (d.compareTo(per.getCedula()) == 0) {
                        PersonaVotante e1 = (PersonaVotante) aux.getInfo();
                        //per = (Persona) ant.getInfo();
                        System.out.print(" ant " + per.getCedula() + " -----  act" + e1.getCedula());
                        return ant;

                    }

                    if (d.compareTo(per.getCedula()) > 0) {
                        ant = aux;
                        aux = aux.getDer();
                        if (aux != null) {
                            per = (PersonaVotante) aux.getInfo();
                        }
                    }
                }
            }

        }

        return ant;
    }

    private boolean NodoNoHoja(String ced) {
        Nodo b = buscarNodo(ced);
        if (b != null) {
            if (b.getIzq() != null || b.getDer() != null) {
                return true;
            }
        }
        return false;
    }

    private void Dañar(Nodo nodo) {
        nodo = null; //la hoja se le apunta a null
        //hoja izquierada
        if (anterior != null && anterior.getIzq() == aux1) {
            anterior.setIzq(nodo);
        }
        //hoja derecha
        if (anterior != null && anterior.getDer() == aux1) {
            anterior.setDer(nodo);
        }
        //hoja raiz
        if (anterior == null) {
            raiz = null;

        }
    }

    private boolean Enlazar(Nodo nodo) {
        if (anterior != null) {
            if (anterior.getIzq() == aux1) {
                aux1 = nodo;
                anterior.setIzq(aux1);
                return true;
            } else {
                aux1 = nodo;
                anterior.setDer(aux1);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarHoja(Nodo Padre, String dato) {
        if (Padre.getDer() != null) {
            PersonaVotante o = (PersonaVotante) Padre.getDer().getInfo();
            if (dato.equals(o.getCedula())) {
                Padre.setDer(null);
                return true;
            }
        }
        if (Padre.getIzq() != null) {
            PersonaVotante o = (PersonaVotante) Padre.getIzq().getInfo();
            if (dato.equals(o.getCedula())) {
                Padre.setIzq(null);
                return true;
            }
        }
        return false;
    }

    public boolean intercambioHoja(String dato) {
        Nodo x = buscarNodo(dato);
        Nodo r = null;
        if (x.getIzq() != null) {
            r = x.getIzq();
            while (r.getDer() != null) {
                r = r.getDer();
            }
        } else {
            if (x.getDer() != null) {
                Nodo z = buscarNodoPadre(dato);
                PersonaVotante p = (PersonaVotante) x.getIzq().getInfo();
                if (p.getCedula() == dato) {
                    z.setIzq(x.getDer());
                }
            }
        }

        PersonaVotante temp = (PersonaVotante) r.getInfo();
        Nodo t = buscarNodoPadre(temp.getCedula());
        Object z = x.getInfo();
        x.setInfo(r.getInfo());
        r.setInfo(z);
        return eliminarHoja(t, dato);
    }

    public boolean eliminarRaiz(Nodo raiz, String dato) {
        if (raiz.getDer() != null) {
            Nodo a = raiz.getDer();
            Nodo ant = a;
            while (a.getIzq() != null) {
                ant = a;
                a = a.getIzq();
            }
            PersonaVotante k = (PersonaVotante) a.getInfo();
            Object res = raiz.getInfo();
            raiz.setInfo(a.getInfo());
            a.setInfo(res);
            if (ant.getIzq() != null) {
                if (NodoNoHoja(k.getCedula())) {
                    intercambioHoja(k.getCedula());
                } else {
                    eliminarHoja(ant, dato);
                }
            }
            if (NodoNoHoja(k.getCedula())) {
                intercambioHoja(k.getCedula());
            } else {
                eliminarHoja(buscarNodoPadre(k.getCedula()), k.getCedula());
            }
        } else {
            raiz = null;
            return true;
        }
        return false;
    }

    public boolean EliminarNodos(String dato) {
        Nodo aux3 = null;
        if (!NodoNoHoja(dato)) {
            aux3 = buscarNodoPadre(dato);
            return eliminarHoja(aux3, dato);
        } else {
            if (buscarNodoPadre(dato) != null) {
                return intercambioHoja(dato);
            } else {
                return eliminarRaiz(buscarNodo(dato), dato);
            }
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }
}
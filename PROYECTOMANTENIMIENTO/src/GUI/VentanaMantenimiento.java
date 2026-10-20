
package GUI;
import javax.swing.*;
import javax.swing.table.*;
import DAO.*;
import java.awt.event.*;
import java.util.*;
import BEAN.*;
public class VentanaMantenimiento extends JFrame implements ActionListener, MouseListener{
private JLabel lblCodigo;
private JLabel lblNombre;
private JTextField txtCodigo;
private JTextField txtNombre;
private JTable tabla;
private JScrollPane Panel;
private JButton btnModificar;
private JButton btnEliminar;
private JButton btnAgregar;
private JTextField txtApellido;
private JLabel lblApellido;
DefaultTableModel tablaModel;
PersonaDAO objOperacionPersona;
Persona objPersona;
ArrayList<Persona> lista;
String columnas[]={"CODIGO","NOMBRE","APELLIDO"};
public VentanaMantenimiento(){
    initGUI();
    objOperacionPersona=new PersonaDAO();
    Listar ();
}
public void initGUI(){
    setSize(400,392);
    setLayout(null);
    lblCodigo=new JLabel();
    getContentPane().add(lblCodigo);
    lblCodigo.setText("Codigo");
    lblCodigo.setBounds(40,24,50,14);
    lblNombre=new JLabel();
    getContentPane().add(lblNombre);
    lblNombre.setText("Nombre");
    lblNombre.setBounds(40,58,57,14);
    lblApellido=new JLabel();
    getContentPane().add(lblApellido);
    lblApellido.setText("Apellido");
    lblApellido.setBounds(40,94,50,14);
    txtCodigo=new JTextField();
    getContentPane().add(txtCodigo);
    txtCodigo.setBounds(88, 21,80, 21);
    txtNombre=new JTextField();
    getContentPane().add(txtNombre);
    txtNombre.setBounds(88, 55,159, 21);
    txtApellido=new JTextField();
    getContentPane().add(txtApellido);
    txtApellido.setBounds(88, 91,226, 21);
    btnAgregar= new JButton();
    getContentPane().add(btnAgregar);
    btnAgregar.setText("Agregar");
    btnAgregar.setBounds(40, 139, 100, 21);
    btnAgregar.addActionListener(this);
    btnEliminar= new JButton();
    getContentPane().add(btnEliminar);
    btnEliminar.setText("Eliminar");
    btnEliminar.setBounds(142, 139, 111, 21);
    btnEliminar.addActionListener(this);
    btnModificar= new JButton();
    getContentPane().add(btnModificar);
    btnModificar.setText("Modificar");
    btnModificar.setBounds(256, 139, 102, 21);
    btnModificar.addActionListener(this);
    Panel= new JScrollPane();
    getContentPane().add(Panel);
    Panel.setBounds(40,171,331,181);
    tablaModel= new DefaultTableModel(null, columnas);
    tabla= new JTable();
    Panel.setViewportView(tabla);
    tabla.addMouseListener(this);
    tabla.setModel(tablaModel);
}    
public static void main (String[] args){
    VentanaMantenimiento inst= new VentanaMantenimiento();
    inst.setVisible(true);
}
public void Listar (){
    int i=0;
    try{
        lista= objOperacionPersona.cargarLista();
        tablaModel.setNumRows(lista.size());
        for (Persona objPerso:lista){
            tablaModel.setValueAt(""+objPerso.getCodigo(), i, 0);
            tablaModel.setValueAt(objPerso.getNombre(), i, 1);
            tablaModel.setValueAt(objPerso.getApellido(), i, 2);
            i++;
        }
        tabla.setModel(tablaModel);
    }
    catch(Exception e){
    }
}
public void LimpiarCampos(){
    txtCodigo.setText("");
    txtNombre.setText("");
    txtApellido.setText("");
    txtCodigo.requestFocus();
}
public void InsertarDatos(){
    int i=0;
    objPersona=null;
           objPersona= new Persona();
           objPersona.setCodigo(Integer.parseInt(txtCodigo.getText()));
           objPersona.setNombre(txtNombre.getText());
           objPersona.setApellido(txtApellido.getText());
           i=objOperacionPersona.guardarDatos(objPersona);
           switch(i){
               case 1:{
                   JOptionPane.showMessageDialog(null, "Se inserto los datos");
                   break;
               }
               default:{
                   JOptionPane.showMessageDialog(null, "No se inserto los datos");
               }
           }
           LimpiarCampos();
}
public void seleccionar(){
    try{
        txtCodigo.setText(tabla.getValueAt(tabla.getSelectedRow(),0).toString());
        txtNombre.setText(tabla.getValueAt(tabla.getSelectedRow(),1).toString());
        txtApellido.setText(tabla.getValueAt(tabla.getSelectedRow(),2).toString());
        btnAgregar.setEnabled(false);
    }catch(Exception e){
    }
}
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnAgregar){
            InsertarDatos();
            Listar();
        }
        if (e.getSource()==btnModificar){
            actualizarDatos();
            Listar();
        }
        if (e.getSource()==btnEliminar){
            borrarDato();
            Listar();
        }
    }
public void borrarDato(){
    int i=0;
    objPersona=null;
    if(txtCodigo.getText().length()!=0){
        objPersona= new Persona();
        objPersona.setCodigo(Integer.parseInt(txtCodigo.getText()));
        i=objOperacionPersona.eliminarDatos(objPersona);
        switch(i){
            case 1:{
                JOptionPane.showMessageDialog(null,"Registro Eliminado");
                break;
            }
            default:{
                JOptionPane.showMessageDialog(null, "Registro no eliminado");
            }
        }
    }
    else{
        JOptionPane.showMessageDialog(null, "Seleccion por favor un registro para eliminar");
    }
    LimpiarCampos();
    btnAgregar.setEnabled(true);
}
public void actualizarDatos(){
    int i=0;
    objPersona=null;
    if(txtCodigo.getText().length()!=0){
        objPersona= new Persona();
        objPersona.setCodigo(Integer.parseInt(txtCodigo.getText()));
        objPersona.setNombre(txtNombre.getText());
        objPersona.setApellido(txtApellido.getText());
        i = objOperacionPersona.modificarDatos(objPersona);
        switch(i){
            case 1:{
                JOptionPane.showMessageDialog(null,"Se modifico los datos");
                break;
            }
            default:{
                JOptionPane.showMessageDialog(null, "No se modificaron los datos");
            }
        }
    }
    else{
        JOptionPane.showMessageDialog(null, "Seleccion por favor un registro para modificar");
    }
    LimpiarCampos();
    btnAgregar.setEnabled(true);
}
    @Override
    public void mouseClicked(MouseEvent e) {
    if (e.getSource()==tabla){
        seleccionar();
    }}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e){}


}
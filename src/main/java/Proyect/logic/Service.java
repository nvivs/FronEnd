package Proyect.logic;

import BackEnd.data.PDFgenerator;
import Protocol.*;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Service implements IService, IListener{  // PROXY

    private static Service theInstance;
    ObjectSocket ss = null;
    ObjectSocket as = null;
    ITarget target;

    public static IService instance(){
        if (theInstance==null){
            theInstance=new Service();
        }
        return theInstance;
    }

    public static IListener instanceListener(){
        if (theInstance==null){
            theInstance=new Service();
        }
        return theInstance;
    }

    public Service() {
        try{
            this.connect();
        }catch(Exception e){
            System.exit(-1);
        }
    }

    private void connect() throws Exception{
        ss = new ObjectSocket(new Socket(Protocol.SERVER, Protocol.PORT));
        ss.out.writeInt(Protocol.SYNC);
        ss.out.flush();
        ss.sid = (String) ss.in.readObject();
    }

    private void disconnect() throws Exception{
        ss.out.writeInt(Protocol.DISCONNECT);
        ss.out.flush();
        ss.skt.shutdownOutput();
        ss.skt.close();
    }

    // LISTENING FUNCTIONS
    boolean continuar = true;

    public void startListening() {
        try {
            as = new ObjectSocket(new Socket(Protocol.SERVER, Protocol.PORT));
            as.sid = ss.sid;
            as.out.writeInt(Protocol.ASYNC);
            as.out.writeObject(as.sid);
            as.out.flush();
        }catch (IOException e){
            System.out.println("Error");
        }

        Thread t = new Thread(new Runnable(){
            public void run(){
                listen();
            }
        });
        continuar = true;
        t.start();
    }

    public void stopListening(){
        continuar=false;
    }

    public void listen(){
        int method;
        while (continuar) {
            try {
                method = as.in.readInt();
                switch(method){
                    case Protocol.DELIVER:
                        try {
                            Message message=(Message)as.in.readObject();
                            deliver(message);
                        } catch (ClassNotFoundException ex) {}
                        break;
                }
            } catch (IOException ex) {
                continuar = false;
            }
        }

        try{
            as.skt.shutdownOutput();
            as.skt.close();
        }catch (IOException e){}
    }

    private void deliver( final Message message ){
        SwingUtilities.invokeLater(new Runnable(){
               public void run(){
                   target.deliver(message);
               }
           }
        );
    }

    @Override
    public void start(){
        this.startListening();
    }

    @Override
    public void stop(){
        this.stopListening();
        try{
            this.disconnect();
        }catch (Exception e){}
    }

    @Override
    public void addTarget(ITarget target) {
        this.target=target;
    }

    @Override
    public void create(InstrumentTypes instrumentTypes) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_TYPE_CREATE);
        ss.out.writeObject(instrumentTypes);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_ERROR){throw new Exception("TIPO INSTRUMENTO DUPLICADO");} // Para testear, pero igual no funciona xd
    }

    @Override
    public InstrumentTypes read(InstrumentTypes instrumentTypes) throws Exception {

        ss.out.writeInt(Protocol.INSTRUMENT_TYPE_READ);
        ss.out.writeObject(instrumentTypes);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){ return (InstrumentTypes) ss.in.readObject();}
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public InstrumentTypes readName(InstrumentTypes instrumentTypes) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_TYPE_READ_NAME);
        ss.out.writeObject(instrumentTypes);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){ return (InstrumentTypes) ss.in.readObject();}
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public void update(InstrumentTypes instrumentTypes) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_TYPE_UPDATE);
        ss.out.writeObject(instrumentTypes);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL GUARDAR");
    }

    @Override
    public void delete(InstrumentTypes instrumentTypes) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_TYPE_DELETE);
        ss.out.writeObject(instrumentTypes);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINAR");
    }

    @Override
    public List<InstrumentTypes> search(InstrumentTypes instrumentTypes) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_TYPE_SEARCH);
        ss.out.writeObject(instrumentTypes);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){ return (List<InstrumentTypes>) ss.in.readObject();}
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public List<InstrumentTypes> refresh() throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_TYPE_REFRESH);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){ return (List<InstrumentTypes>) ss.in.readObject();}
        else throw new Exception("ERROR AL ACTUALIZAR");
    }

    @Override
    public void create(Instrument instrument) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_CREATE);
        ss.out.writeObject(instrument);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("INSTRUMENTO DUPLICADO");
    }

    @Override
    public Instrument read(Instrument instrument) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_READ);
        ss.out.writeObject(instrument);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){return (Instrument) ss.in.readObject();}
        else throw new Exception("INSTRUMENTO NO EXISTE");
    }

    @Override
    public void update(Instrument instrument) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_UPDATE);
        ss.out.writeObject(instrument);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERRO AL GUARDAR");
    }

    @Override
    public void delete(Instrument instrument) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_DELETE);
        ss.out.writeObject(instrument);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINIAR INSTRUMENTO");
    }

    @Override
    public List<Instrument> searchDescription(Instrument instrument) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_SEARCH_DESCRIPTION);
        ss.out.writeObject(instrument);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){ return (List<Instrument>) ss.in.readObject();}
        else throw new Exception("INSTRUMENTO NO EXISTE");
    }

    @Override
    public List<Instrument> searchSerialNumber(Instrument instrument) throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_SEARCH_SERIAL_NUMBER);
        ss.out.writeObject(instrument);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Instrument>) ss.in.readObject(); }
        else throw new Exception("INSTRUMENTO NO EXISTE");
    }

    @Override
    public List<Instrument> refreshInstrument() throws Exception {
        ss.out.writeInt(Protocol.INSTRUMENT_REFRESH);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){ return (List<Instrument>) ss.in.readObject();}
        else throw new Exception("ERROR AL ACTUALIZAR");
    }

    @Override
    public List<Instrument> getALLInstruments() throws Exception{
        ss.out.writeInt(Protocol.INSTRUMENT_GET_ALL);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Instrument>) ss.in.readObject(); }
        else throw new Exception("ERROR AL OBTENER LOS INSTRUMENTOS");
    }

    @Override
    public void create(Calibrations calibrations) throws Exception {
        ss.out.writeInt(Protocol.CALIBRATIONS_CREATE);
        ss.out.writeObject(calibrations);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("CALIBRACION DUPLICADA");
    }

    @Override
    public Calibrations read(Calibrations calibrations) throws Exception {
        ss.out.writeInt(Protocol.CALIBRATIONS_READ);
        ss.out.writeObject(calibrations);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){return (Calibrations) ss.in.readObject();}
        else throw new Exception("CALIBRACION NO EXISTE");
    }

    @Override
    public void delete(Calibrations calibrations) throws Exception {
        ss.out.writeInt(Protocol.CALIBRATIONS_DELETE);
        ss.out.writeObject(calibrations);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINAR CALIBRACION");
    }

    @Override
    public List<Calibrations> search(Calibrations calibrations) throws Exception {
        ss.out.writeInt(Protocol.CALIBRATIONS_SEARCH);
        ss.out.writeObject(calibrations);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Calibrations>) ss.in.readObject(); }
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public int getCalibrationIndex() throws Exception {
        ss.out.writeInt(Protocol.CALIBRATIONS_GET_CALIBRATION_INDEX);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){ return ss.in.readInt();}
        else throw new Exception("ERROR AL OBTENER INDEX");
    }

    @Override
    public List<Calibrations> getAllCalibrations(Calibrations e) throws Exception{
        ss.out.writeInt(Protocol.CALIBRATIONS_GET_ALL);
        ss.out.writeObject(e);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Calibrations>) ss.in.readObject(); }
        else throw new Exception("ERROR AL OBTENER LAS CALIBRACIONES");
    }

    @Override
    public void create(Measures measures, int i) throws Exception {
        ss.out.writeInt(Protocol.MEASURES_CREATE);
        ss.out.writeObject(measures);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("MEDICION DUPLICADA");
    }

    @Override
    public void delete(int i) throws Exception {
        ss.out.writeInt(Protocol.MEASURES_DELETE);
        ss.out.writeInt(i);
        ss.out.flush();
        if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINAR");
    }

    @Override
    public void imprimir(List<Object> list, String s) throws Exception {
        PDFgenerator.generate(list, s);
    }

    @Override
    public void update(List<Measures> list, int i) throws Exception {
            ss.out.writeInt(Protocol.MEASURES_UPDATE);
            ss.out.writeObject(list);
            ss.out.writeInt(i);
            ss.out.flush();
            if(ss.in.readInt()==Protocol.ERROR_NO_ERROR){}
            else throw new Exception("ERROR AL ACTUALIZAR");
        }
}

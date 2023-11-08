package Proyect.logic;

import Protocol.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import javax.swing.SwingUtilities;

public class Service implements IService{  // PROXY
    private static Service theInstance;
    public static Service instance(){
        if (theInstance==null){
            theInstance=new Service();
        }
        return theInstance;
    }

    ObjectSocket os = null;
    public Service() {
        try{
            this.connect();
        }catch(Exception e){
            System.exit(-1);
        }
    }

    private void connect() throws Exception{
        Socket skt;
        skt = new Socket(Protocol.SERVER,Protocol.PORT);
        os = new ObjectSocket(skt);
    }

    private void disconnect() throws Exception{
        os.skt.shutdownOutput();
        os.skt.close();
    }

    @Override
    public void create(InstrumentTypes instrumentTypes) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_TYPE_CREATE);
        os.out.writeObject(instrumentTypes);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_ERROR){throw new Exception("TIPO INSTRUMENTO DUPLICADO");} // Para testear, pero igual no funciona xd
    }

    @Override
    public InstrumentTypes read(InstrumentTypes instrumentTypes) throws Exception {

        os.out.writeInt(Protocol.INSTRUMENT_TYPE_READ);
        os.out.writeObject(instrumentTypes);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){ return (InstrumentTypes) os.in.readObject();}
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public InstrumentTypes readName(InstrumentTypes instrumentTypes) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_TYPE_READ_NAME);
        os.out.writeObject(instrumentTypes);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){ return (InstrumentTypes) os.in.readObject();}
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public void update(InstrumentTypes instrumentTypes) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_TYPE_UPDATE);
        os.out.writeObject(instrumentTypes);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL GUARDAR");
    }

    @Override
    public void delete(InstrumentTypes instrumentTypes) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_TYPE_DELETE);
        os.out.writeObject(instrumentTypes);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINAR");
    }

    @Override
    public List<InstrumentTypes> search(InstrumentTypes instrumentTypes) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_TYPE_SEARCH);
        os.out.writeObject(instrumentTypes);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){ return (List<InstrumentTypes>) os.in.readObject();}
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public List<InstrumentTypes> refresh() throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_TYPE_REFRESH);
      //  os.out.writeObject();
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){ return (List<InstrumentTypes>) os.in.readObject();}
        else throw new Exception("ERROR AL ACTUALIZAR");
    }

    @Override
    public void create(Instrument instrument) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_CREATE);
        os.out.writeObject(instrument);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("INSTRUMENTO DUPLICADO");
    }

    @Override
    public Instrument read(Instrument instrument) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_READ);
        os.out.writeObject(instrument);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){return (Instrument) os.in.readObject();}
        else throw new Exception("INSTRUMENTO NO EXISTE");
    }

    @Override
    public void update(Instrument instrument) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_UPDATE);
        os.out.writeObject(instrument);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERRO AL GUARDAR");
    }

    @Override
    public void delete(Instrument instrument) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_DELETE);
        os.out.writeObject(instrument);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINIAR INSTRUMENTO");
    }

    @Override
    public List<Instrument> searchDescription(Instrument instrument) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_SEARCH_DESCRIPTION);
        os.out.writeObject(instrument);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){ return (List<Instrument>) os.in.readObject();}
        else throw new Exception("INSTRUMENTO NO EXISTE");
    }

    @Override
    public List<Instrument> searchSerialNumber(Instrument instrument) throws Exception {
        os.out.writeInt(Protocol.INSTRUMENT_SEARCH_SERIAL_NUMBER);
        os.out.writeObject(instrument);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Instrument>) os.in.readObject(); }
        else throw new Exception("INSTRUMENTO NO EXISTE");
    }

    @Override
    public List<Instrument> refreshInstrument() throws Exception {
        //eliminar?
        return null;
    }

    @Override
    public void create(Calibrations calibrations) throws Exception {
        os.out.writeInt(Protocol.CALIBRATIONS_CREATE);
        os.out.writeObject(calibrations);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("CALIBRACION DUPLICADA");
    }

    @Override
    public Calibrations read(Calibrations calibrations) throws Exception {
        os.out.writeInt(Protocol.CALIBRATIONS_READ);
        os.out.writeObject(calibrations);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){return (Calibrations) os.in.readObject();}
        else throw new Exception("CALIBRACION NO EXISTE");
    }

    @Override
    public void delete(Calibrations calibrations) throws Exception {
        os.out.writeInt(Protocol.CALIBRATIONS_DELETE);
        os.out.writeObject(calibrations);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINAR CALIBRACION");
    }

    @Override
    public List<Calibrations> search(Calibrations calibrations) throws Exception {
        os.out.writeInt(Protocol.CALIBRATIONS_SEARCH);
        os.out.writeObject(calibrations);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Calibrations>) os.in.readObject(); }
        else throw new Exception("TIPO INSTRUMENTO NO EXISTE");
    }

    @Override
    public int getCalibrationIndex() throws Exception {
        /*os.out.writeInt(Protocol.CALIBRATIONS_GET_CALIBRATION_INDEX);
       // os.out.writeObject(instrument);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){ return }
        else throw new Exception("ERROR AL OBTENER INDEX");

         */
        return 0;
    }

    @Override
    public List<Calibrations> getList(Calibrations calibrations) throws Exception {
        os.out.writeInt(Protocol.CALIBRATIONS_GET_LIST);
        os.out.writeObject(calibrations);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Calibrations>) os.in.readObject(); }
        else throw new Exception("NO EXISTEN CALIBRACIONES");
    }

    @Override
    public List<Calibrations> refreshCalibracion(Calibrations calibrations) throws Exception {
        os.out.writeInt(Protocol.CALIBRATIONS_REFRESH);
        os.out.writeObject(calibrations);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){return (List<Calibrations>) os.in.readObject(); }
        else throw new Exception("ERROR AL ACTUALIZAR");
    }

    @Override
    public void create(Measures measures, int i) throws Exception {
        os.out.writeInt(Protocol.MEASURES_CREATE);
        os.out.writeObject(measures);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("MEDICION DUPLICADA");
    }

    @Override
    public void delete(int i) throws Exception {
        os.out.writeInt(Protocol.MEASURES_DELETE);
        os.out.writeInt(i);
        os.out.flush();
        if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
        else throw new Exception("ERROR AL ELIMINAR");
    }

    @Override
    public void imprimir(List<Object> list, String s) throws Exception {
           //??
    }

    @Override
    public void update(List<Measures> list, int i) throws Exception {
            os.out.writeInt(Protocol.MEASURES_UPDATE);
            os.out.writeObject(list);
            os.out.writeInt(i);
            os.out.flush();
            if(os.in.readInt()==Protocol.ERROR_NO_ERROR){}
            else throw new Exception("ERROR AL ACTUALIZAR");
        }
}

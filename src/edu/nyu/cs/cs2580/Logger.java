package edu.nyu.cs.cs2580;

import java.util.Vector;
import java.util.HashMap;
import java.util.Date;

class Logger{
    private static HashMap <Integer, Vector<Logger>> _logs = new HashMap <Integer, Vector<Logger>>();
    private static int _currSid = 0;

    public int _sid;
    public int _did;
    public String _query;
    public String _action;
    public Date _time;

    public Logger(int _sid, String _query, int _did, String _action){
        this._sid = _sid;
        this._did = _did;
        this._query = _query;
        this._action = _action;
        this._time = new Date();
    }

    public static void addLog(int _sid, String _query, int _did, String _action){
        System.out.println("add log: " + _sid +" " + _query +" "+ _did +" "+_action);
        Logger log = new Logger(_sid, _query, _did, _action);
        if(_logs.containsKey(_sid) == false){
            _logs.put(_sid, new Vector<Logger>());
        }
        _logs.get(_sid).add(log);
    }

    public static int getSid(){
        return _currSid++;
    }
    public static Vector<Logger> getLog(int sid){
        Vector<Logger> logs = new Vector<Logger>();
        if(_logs.containsKey(sid)){
            logs = (Vector)_logs.get(sid).clone();
        }
        return logs;
    }
    public static Vector<Logger> getAllLog(){
        Vector<Logger> logs = new Vector<Logger>();
        for(int key : _logs.keySet()){
            logs.addAll(_logs.get(key));
        }
        return logs;
    }
}

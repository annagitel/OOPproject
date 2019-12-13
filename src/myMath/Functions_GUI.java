package myMath;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.awt.Font;
import static java.awt.Font.*;
import com.google.gson.*;

import javax.imageio.event.IIOReadProgressListener;

public class Functions_GUI implements  functions {
    private ArrayList<function> funcList = new ArrayList<>();
    public static Color[] colors = {Color.BLUE, Color.YELLOW, Color.PINK, Color.RED, Color.GREEN};
    /***********************************Implemented from functions***************************/
    @Override
    public void initFromFile(String file) throws IOException {
        String str = "";
        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            while ((str = b.readLine()) !=  null){
                String[] funcs = str.split("\n");
                for (int i=0;i<funcs.length; i++)
                    funcList.add(new ComplexFunction(funcs[i]));
            }
            b.close();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IOException("There was a problem with the file");
        }
    }

    @Override
    public void saveToFile(String file) throws IOException {
        String str = "";
        Iterator<function> it = this.iterator();
        while (it.hasNext()) {
            function func = it.next();
            str += (func.toString()+"\n");
        }

        try{
            PrintWriter p = new PrintWriter(new File(file));
            p.write(str);
            p.close();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IOException("There was a problem with the file");
        }
    }

    @Override
    public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
        StdDraw.setCanvasSize(width,height);                                           // set canvas size
        StdDraw.setYscale(ry.get_min(),ry.get_max());                                  // set X line
        StdDraw.setXscale(rx.get_min(),rx.get_max());                                  // set Y line

        StdDraw.setPenRadius(0.001);                                                   // set pen radius
        StdDraw.setPenColor(Color.LIGHT_GRAY);                                         // set pen color

        for (int i = (int) rx.get_min(); i<= rx.get_max(); i++)                        // draw horizontal lines
            StdDraw.line(i, ry.get_min(), i, ry.get_max());

        for (int i = (int) ry.get_min(); i<= ry.get_max(); i++)                        // draw vertical lines
            StdDraw.line(rx.get_min(),i,rx.get_max(),i);

        StdDraw.setPenRadius(0.003);                                                   // set pen radius
        StdDraw.setPenColor(Color.DARK_GRAY);                                          // set pen color
        StdDraw.line(0, ry.get_min(), 0, ry.get_max());                        // draw X line
        StdDraw.line(rx.get_min(),0, rx.get_max(),0);                          // draw Y line

        StdDraw.setFont(new Font("Ariel", BOLD, 10));                      //set font
        for (double i = ry.get_min(); i<= ry.get_max(); i++)                           // draw numbers for y
            StdDraw.text(-0.25, i, Double.toString(i));
        for (double i = rx.get_min(); i<= rx.get_max(); i++)                           // draw numbers for x
            StdDraw.text(i, -0.25, Double.toString(i));

        double delta = rx.get_max()-rx.get_min()/resolution;
        Iterator<function> it = this.iterator();
        while (it.hasNext()){
            function current = it.next();
            StdDraw.setPenColor(colors[(int) (Math.random()*5)]);
            StdDraw.setPenRadius(0.005);
            for(double i= rx.get_min(); i<=rx.get_max(); i+=delta)
                StdDraw.line(i, current.f(i),i+delta, current.f(i+delta));

        }
    }

    @Override
    public void drawFunctions(String json_file) {
        Gson gson = new Gson();
        guiParams parameters = null;
        try {
            FileReader fr = new FileReader(json_file);
            parameters = gson.fromJson(fr, guiParams.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("your JSON format is not valid, initing from default parameters");
            int Width = 1000;
            int Height = 600;
            double[] Range_X = {-10, 10};
            double[] Range_Y = {-10, 10};
            int Resolution = 200;
            parameters = new guiParams(Width,Height,Resolution,Range_X,Range_Y);
        }
        Range rx = new Range(parameters.xRange[0], parameters.xRange[1]);
        Range ry = new Range(parameters.yRange[0], parameters.yRange[1]);
        drawFunctions(parameters.width, parameters.heigh, rx, ry, parameters.resolution);

    }

    /***********************************Implemented from Collection***************************/
    @Override
    public int size() {
        return funcList.size();
    }

    @Override
    public boolean isEmpty() {
        return funcList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return funcList.contains(o);
    }

    @Override
    public Iterator<function> iterator() {
        return funcList.iterator();
    }

    @Override
    public Object[] toArray() {
        return funcList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return funcList.toArray(a);
    }

    @Override
    public boolean add(function f) {
        return funcList.add(f);
    }

    @Override
    public boolean remove(Object o) {
        return funcList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return funcList.containsAll(c);
    }

    @Override //adds a list of functions to ths list
    public boolean addAll(Collection<? extends function> c) {
        return funcList.addAll(c);
    }

    @Override //removes all function from list. returns true if process was successful
    public boolean removeAll(Collection<?> c) {
        return funcList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return funcList.retainAll(c);
    }

    @Override //clears all functions from list
    public void clear() {
        funcList.clear();
    }


}

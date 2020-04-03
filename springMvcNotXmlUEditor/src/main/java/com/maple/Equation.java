package com.maple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TYZ034 on 2018/3/14.
 */
public class Equation {
    //方程系数 a,b,c   ax2+bx+c=0
    private String equation;

    private List<String> getRoot() {
        List<String> list = new ArrayList<>();
        String[] quet = equation.split(",");
        if (quet == null || quet.length != 3 || !quet[0].matches("[0-9]+") || !quet[1].matches("[0-9]+") || !quet[2].matches("[0-9]+")) {
            System.out.println("请输入正确的系数！");
            return null;
        }
        int a = Integer.valueOf(quet[0]);
        int b = Integer.valueOf(quet[1]);
        int c = Integer.valueOf(quet[2]);
        int disc = b * b - 4 * a * c;
        if (disc == 0) {
            double x = -b / (2 * a);
            list.add(String.valueOf(x));
            list.add(String.valueOf(x));
        } else {
            double t = Math.sqrt(Math.abs(disc)) / (2 * a);
            if (disc > 0) {
                double x1 = -b / (2 * a) + t;
                double x2 = -b / (2 * a) - t;
                list.add(String.valueOf(x1));
                list.add(String.valueOf(x2));
            }else{
                double x = -b / (2 * a) ;
                list.add(String.valueOf(x) +"+(" + t +")*i");
                list.add(String.valueOf(x) +"-(" + t +")*i");
            }
        }
        return list;
    }

    public  void setEquation(String equation) {
        this.equation = equation;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList();
        Equation equation = new Equation();
        equation.setEquation("1,4,0");
        if((list = equation.getRoot())!=null){
            for(String s : list){
                System.out.println(s);
            }
        }
    }
}

package com.youi.example1.sort;

/**
 * Created by jinliang on 2017/3/6.
 */
public class SortMain {

    private void swap(int[] data,int i,int j){
        int temp = data[i];
        data[i]=data[j];
        data[j]=temp;
    }
    public void print(int[]data){
        for(int i=0;i<data.length;i++){
            System.out.print(data[i]+" ");
        }
        System.out.println();
    }



    public void selectSort(int[] data){
        for(int i=0;i<data.length;i++){
            int temp = data[i];
            int k =i;
            for(int j=i+1;j<data.length;j++){
                if(data[j]<temp){
                    temp=data[j];
                    k=j;
                }
            }
            if(k!=i){
                swap(data,k,i);
            }
        }
        print(data);
    }


    public void insertSort(int[] data){
        for(int i=1;i<data.length;i++){
            int v = data[i];
            int j=i-1;
            while(j>=0&&data[j]>v){
                data[j+1]=data[j];
                j--;
            }
            data[j+1]=v;
        }
        print(data);
    }

    public void quickSort(int[]data,int left,int right){
        int i=left,j=right;
        int m=left;

            int v = data[left];
            while(i<j){
                while(i<j&&data[j]>=v){j--;}
                if(i<j){
                    swap(data,i,j);
                    i++;
                }
                while(i<j&&data[i]<=v){i++;}
                if(i<j){
                    swap(data,i,j);
                    j--;
                }
            }
            m=i;
            if(left<m-1){
                quickSort(data,left,m-1);
            }
            if(m+1<right){
                quickSort(data,m+1,right);
            }

    }

    public void popupSort(int[] data){
        boolean changed=true;
        for(int i=0;i<data.length;i++){
            if(!changed) break;
            changed=false;
            for(int j=0;j<data.length-i-1;j++){
                if(data[j]>data[j+1]){
                    swap(data,j,j+1);
                    changed=true;
                }
            }
        }
        print(data);
    }


    public int[] src_data={2,5,1,9,8,4,3,6,0,7,10};

    public static void main(String[] args) {
        SortMain o = new SortMain();
      //  o.selectSort(o.src_data);
     //  o.insertSort(o.src_data);
    //    o.quickSort(o.src_data,0,o.src_data.length-1);
      // o.print(o.src_data);
       // o.popupSort(o.src_data);
    }

}

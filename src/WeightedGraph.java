import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class WeightedGraph {
        private static int MaxTabu=100;
        private static int MaxIteration=20;
        private static int numOfNodes;
        private static int numOfNeighbors=10;
        private static int[][] matrix;
        private static boolean[][] isSetMatrix;
        public WeightedGraph(int numOfNodes) {
            this.numOfNodes = numOfNodes;
            matrix = new int[numOfNodes][numOfNodes];
            isSetMatrix = new boolean[numOfNodes][numOfNodes];
        }

        public static void addEdge(int source, int destination, int weight) {
            matrix[source][destination] = weight;
            isSetMatrix[source][destination] = true;
        }

    public static boolean hasEdge(int source, int destination) {
        return isSetMatrix[source][destination] ;
    }

    public static int getEdgeValue(int source, int destination) {
            if(hasEdge(source,destination))
                return matrix[source][destination];
            else if(hasEdge(destination,source))
                return matrix[destination][source];
            else
                return 0;
    }
    public static int cost(int[] arrA,int[] arrB){
        int      sum_w=0;
        for( int y=0;y<arrA.length;y++) {
            for (int j = 0; j < arrB.length; j++) {
                sum_w += getEdgeValue(arrA[y], arrB[j]);
            }
        }
        return sum_w;
    }

    public static void gen_init_solution(int[] arrA,int[] arrB,int numOfNodes){
        for( int i=0;i<numOfNodes/2;i++){
            arrA[i]=i;
        }
        int j=numOfNodes/2;
        for(int k=0;k<numOfNodes/2;k++){
            arrB[k]=j;
            j++;
        }
    }

    public static int Toequivalent(int []arr1, int []arr2, int n)
    {
        for (int i = 0; i < n; i++)
        {
            int flag = 0;
            for (int j = 0; j < n; j++)
                if (arr1[i] == arr2[j])
                    flag = 1;
            if (flag == 0)
                return 0;
        }
        return 1;
    }
    public static int isTabu(int[][] TabuList, int[] arrA, int MaxIndex) {

            for (int i = 0; i < MaxIndex; i++)
            {
                if (Toequivalent(TabuList[i], arrA, arrA.length)==1)
                    return 1;
            }
            return 0;
        }
    public static void swap(int[] arrA, int[] arrB,int i, int j ){
        int temp=arrA[i];
        arrA[i]=arrB[j];
        arrB[j]=temp;
    }

    public static int[][] generate_new_neighbor(int[] arrA,int[] arrB, int[][] listTabu,int index_Tabu,int numOfNodes)
    {
        int [] tempArrA=new int[numOfNodes/2];
        int [] tempArrB=new int[numOfNodes/2];
        int indexA=0,indexB=0;


        //remplir les tableaux temporaires par des tablaux reels
        for (int i=0;i<numOfNodes/2;i++) {
            tempArrA[i]=arrA[i];
            tempArrB[i]=arrB[i];
        }
        swap(tempArrA,tempArrB,indexA,indexB);
        indexB++;

        int index=0;
        while (index<numOfNeighbors){
            while (indexB<numOfNodes/2){

                int [] tempArrA1=new int[numOfNodes/2];
                int [] tempArrB1=new int[numOfNodes/2];

                for (int j=0;j<numOfNodes/2;j++) {
                    tempArrA1[j]=arrA[j];
                    tempArrB1[j]=arrB[j];
                }
                swap(tempArrA,tempArrB,indexA,indexB);

                int Min_cout=cost(tempArrA,tempArrB);;
                int cost=cost(tempArrA1,tempArrB1);
                if((cost<Min_cout) && (isTabu(listTabu,tempArrA1,index_Tabu)==0)){
                    for (int i=0;i<numOfNodes/2;i++) {
                        tempArrA[i]=tempArrA1[i];
                        tempArrB[i]=tempArrB1[i];
                    }
                }
                indexB++;
            }
            indexA++;
            indexB = 0;
            if(indexA==numOfNodes/2)
                break;
            index++;
        }
        return new int[][]{tempArrA,tempArrB};

    }

    public static void main(String[] args) {
        int numOfNodesL=6;
        int[][] List_Tabu=new int[MaxTabu][numOfNodesL/2];

        int index_Tabu=0;
            int[] arrA = new int[numOfNodesL/2];
            int[] arrB = new int[numOfNodesL/2];
            int[][] str;

        WeightedGraph graph = new WeightedGraph(numOfNodesL);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 7);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 4, 200);
        graph.addEdge(4, 5, 100);
        graph.addEdge(5, 0, 3);

        gen_init_solution(arrA,arrB,numOfNodesL);

        for(int i=0;i<MaxIteration;i++){
                str=generate_new_neighbor(arrA,arrB,List_Tabu,index_Tabu,numOfNodesL);
            for (int j=0;j<numOfNodesL/2;j++) {
            }
                int cost=cost(str[0],str[1]);
                    if(cost<cost(arrA,arrB)){
                        for (int j=0;j<numOfNodesL/2;j++) {
                            arrA[j]=str[0][j];
                            arrB[j]=str[1][j];
                        }
                    }else{
                        for (int j=0;j<numOfNodesL/2;j++) {
                            List_Tabu[index_Tabu][j]=arrA[j];
                        }
                        for (int j=0;j<numOfNodesL/2;j++) {
                            arrA[j]=str[0][j];
                            arrB[j]=str[1][j];
                        }
                        index_Tabu++;
                        if(index_Tabu==MaxIteration)
                            index_Tabu=0;

                    }
            }
        System.out.print("A : {");
                for (int i:arrA ) {
                    System.out.print(" "+i+" ");
                }
        System.out.println("}");
        System.out.print("B : {");
        for (int i:arrB ) {
            System.out.print(" "+i+" ");
        }
        System.out.println("}");
        System.out.print("cout: "+cost(arrA,arrB));
        }
}








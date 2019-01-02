package com.ycj.paper.experiments.second;

public class KMAlgorithm {

	/**
	 * KM算法C代码如下：
	 * 
	 * 
	 * 
	 * 
#include<algorithm>
#include<iostream>
#include<limits.h>
#include<cstdlib>
#include<cstring>
#include<cassert>
#include<string>
#include<cstdio>
#include<bitset>
#include<vector>
#include<cmath>
#include<ctime>
#include<stack>
#include<queue>
#include<deque>
#include<list>
#include<set>
#define INT 9654234
#define mod 1000000007
typedef long long ll;
using namespace std;
const int MAXN = 305;
int N;
int ex_gir[MAXN];//每个妹子的期望值
int ex_boy[MAXN];//每个男生的期望值
bool vis_gir[MAXN];//记录每一轮匹配过的女生
bool vis_boy[MAXN];//记录每一轮匹配过的男生    每进行新的一轮，都要重新初始化这两个数组
int match[MAXN];//match[i]代表和i男生匹配的女生的编号
int slack[MAXN];//slack[i]代表i男生如果要获得女生的芳心，至少需要增加的期待值
int love[MAXN][MAXN];//记录每个妹子和男生的好感度
bool dfs(int gir)//dfs函数求的是编号为gir的女孩能否匹配到男生，如果能，返回true，否则，返回false
{
    vis_gir[gir]=true;//标记
    for(int i=1;i<=N;i++)
    {
        if(vis_boy[i])//我们规定每次匹配对于某个男生只访问一遍，如果先前访问过了，就换个男生
            continue ;
        int gap=ex_gir[gir]+ex_boy[i]-love[gir][i];
        if(gap==0)//如果这个条件满足，说明编号为gir女孩和编号为i的男孩可能能够匹配成功
        {
            vis_boy[i]=true;//标记
            if(match[i]==-1||dfs(match[i]))//如果这两个条件满足其中一个，说明编号为gir女孩和编号为i的男孩匹配成功
            {
                match[i]=gir;
                return true;
            }
        }
        else
            slack[i]=min(slack[i],gap);//如果gap不等于0，说明当前状态编号为gir女孩和编号为i的男孩不可能匹配成功，更新slack[i]。
    }
    return false;
}
int km()
{
    memset(match,-1,sizeof(match));
    memset(ex_boy,0,sizeof(ex_boy));
    for(int i=1;i<=N;i++)
        for(int j=1;j<=N;j++)
            ex_gir[i]=max(love[i][j],ex_gir[i]);//初始化ex_gir数组
    for(int i=1;i<=N;i++)
    {
        fill(slack,slack+N+1,INT);
        while (1)//这个while循环结束的条件是直到让编号为i的女生找到可以匹配的男生后
        {
            memset(vis_gir,false,sizeof(vis_gir));
            memset(vis_boy,false,sizeof(vis_gir));
            if(dfs(i))//如果这个条件满足，说明编号为i的女生找到了匹配的男生，换下一个女生,如果这个条件不满足，说明这个女生没有匹配到男生，让这个女生降低期望值后继续匹配
                break ;
            int t=INT;
            for(int j=1;j<=N;j++)//寻找在这一轮匹配中没有匹配到的男生如果要获得女生芳心所需要增加的期待值的最小值
                if(!vis_boy[j])
                    t=min(t,slack[j]);
            for(int i=1;i<=N;i++)//让在这一轮匹配中匹配过的女生的期待值减小，匹配过的男生的期待值增加
            {
                if(vis_gir[i])
                    ex_gir[i]-=t;
                if(vis_boy[i])
                    ex_boy[i]+=t;
                else
                    slack[i]-=t;//因为有些女生的期待值减小了，所以这一轮没有被匹配过的男生得到女生的芳心所需要增加的期待值就变小了，所以slack数组中的相应的值要变小
            }
        }
    }
    int res=0;//计算好感和
    for(int i=1;i<=N;i++)
        res+=love[match[i]][i];
    return res;
}
int main()
{
    cin>>N;
    for(int i=1;i<=N;i++)
        for(int j=1;j<=N;j++)
            cin>>love[i][j];
    cout<<km()<<endl;
}

 */
}

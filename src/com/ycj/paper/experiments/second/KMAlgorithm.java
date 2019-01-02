package com.ycj.paper.experiments.second;

public class KMAlgorithm {

	/**
	 * KM�㷨C�������£�
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
int ex_gir[MAXN];//ÿ�����ӵ�����ֵ
int ex_boy[MAXN];//ÿ������������ֵ
bool vis_gir[MAXN];//��¼ÿһ��ƥ�����Ů��
bool vis_boy[MAXN];//��¼ÿһ��ƥ���������    ÿ�����µ�һ�֣���Ҫ���³�ʼ������������
int match[MAXN];//match[i]�����i����ƥ���Ů���ı��
int slack[MAXN];//slack[i]����i�������Ҫ���Ů���ķ��ģ�������Ҫ���ӵ��ڴ�ֵ
int love[MAXN][MAXN];//��¼ÿ�����Ӻ������ĺøж�
bool dfs(int gir)//dfs��������Ǳ��Ϊgir��Ů���ܷ�ƥ�䵽����������ܣ�����true�����򣬷���false
{
    vis_gir[gir]=true;//���
    for(int i=1;i<=N;i++)
    {
        if(vis_boy[i])//���ǹ涨ÿ��ƥ�����ĳ������ֻ����һ�飬�����ǰ���ʹ��ˣ��ͻ�������
            continue ;
        int gap=ex_gir[gir]+ex_boy[i]-love[gir][i];
        if(gap==0)//�������������㣬˵�����ΪgirŮ���ͱ��Ϊi���к������ܹ�ƥ��ɹ�
        {
            vis_boy[i]=true;//���
            if(match[i]==-1||dfs(match[i]))//���������������������һ����˵�����ΪgirŮ���ͱ��Ϊi���к�ƥ��ɹ�
            {
                match[i]=gir;
                return true;
            }
        }
        else
            slack[i]=min(slack[i],gap);//���gap������0��˵����ǰ״̬���ΪgirŮ���ͱ��Ϊi���к�������ƥ��ɹ�������slack[i]��
    }
    return false;
}
int km()
{
    memset(match,-1,sizeof(match));
    memset(ex_boy,0,sizeof(ex_boy));
    for(int i=1;i<=N;i++)
        for(int j=1;j<=N;j++)
            ex_gir[i]=max(love[i][j],ex_gir[i]);//��ʼ��ex_gir����
    for(int i=1;i<=N;i++)
    {
        fill(slack,slack+N+1,INT);
        while (1)//���whileѭ��������������ֱ���ñ��Ϊi��Ů���ҵ�����ƥ���������
        {
            memset(vis_gir,false,sizeof(vis_gir));
            memset(vis_boy,false,sizeof(vis_gir));
            if(dfs(i))//�������������㣬˵�����Ϊi��Ů���ҵ���ƥ�������������һ��Ů��,���������������㣬˵�����Ů��û��ƥ�䵽�����������Ů����������ֵ�����ƥ��
                break ;
            int t=INT;
            for(int j=1;j<=N;j++)//Ѱ������һ��ƥ����û��ƥ�䵽���������Ҫ���Ů����������Ҫ���ӵ��ڴ�ֵ����Сֵ
                if(!vis_boy[j])
                    t=min(t,slack[j]);
            for(int i=1;i<=N;i++)//������һ��ƥ����ƥ�����Ů�����ڴ�ֵ��С��ƥ������������ڴ�ֵ����
            {
                if(vis_gir[i])
                    ex_gir[i]-=t;
                if(vis_boy[i])
                    ex_boy[i]+=t;
                else
                    slack[i]-=t;//��Ϊ��ЩŮ�����ڴ�ֵ��С�ˣ�������һ��û�б�ƥ����������õ�Ů���ķ�������Ҫ���ӵ��ڴ�ֵ�ͱ�С�ˣ�����slack�����е���Ӧ��ֵҪ��С
            }
        }
    }
    int res=0;//����øк�
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

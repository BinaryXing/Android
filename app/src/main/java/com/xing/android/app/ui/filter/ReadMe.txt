该Filter框架需要注意的点
1.该框架主要定义了筛选项之间的关系，View高度自定义；
2.FilterGroupView内keep了一个FilterGroup，FilterGroup和FilterGroupView是一对多的关系；
3.Filter（SelectFilter，ScopeFilter）内keep了FilterView，FilterView（SelectFilterView，ScopeFilterView）内keep了Filter，Filter和FilterView是一对一的关系；
4.流程：用户操作-->FilterView(不会直接改变)-->FilterView关联的Filter数据改变-->FilterGroup是否影响到同组的Filter-->Filter更新关联的FilterView的UI
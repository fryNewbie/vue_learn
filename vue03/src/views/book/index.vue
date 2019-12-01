<template>
  <el-container>
    <el-header>
      <el-menu class="el-menu-demo" mode="horizontal" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="1">处理中心</el-menu-item>
        <el-submenu index="2">
          <template slot="title">我的工作台</template>
          <el-menu-item index="2-1">选项1</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
          <el-menu-item index="2-3">选项3</el-menu-item>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项1</el-menu-item>
            <el-menu-item index="2-4-2">选项2</el-menu-item>
            <el-menu-item index="2-4-3">选项3</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="3" disabled>消息中心</el-menu-item>
        <el-menu-item index="4">
          <a href="https://www.ele.me" target="_blank">订单管理</a>
        </el-menu-item>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu default-active="2" class="el-menu-vertical-demo">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <div class="app-container">
          <div class="filter-container" style="margin-bottom:10px">
            <el-input v-model="listQuery.bookName" placeholder="书籍" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
            <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">
              <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />
            </el-select>
            <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查询</el-button>
            <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">新增</el-button>
          </div>
          <el-table :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%;" stripe @sort-change="sortChange">
            <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
              <template slot-scope="{row}">
                <span>{{ row.id }}</span>
              </template>
            </el-table-column>
            <el-table-column label="书籍编号" align="center">
              <template slot-scope="{row}">
                <span>{{ row.bookId }}</span>
              </template>
            </el-table-column>
            <el-table-column label="书籍名称" align="center">
              <template slot-scope="{row}">
                <span>{{ row.bookName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="作者" align="center">
              <template slot-scope="{row}">
                <span>{{ row.author }}</span>
              </template>
            </el-table-column>
            <el-table-column label="价格" align="center">
              <template slot-scope="{row}">
                <span>{{ row.price }}</span>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center">
              <template slot-scope="{row}">

                <span>{{ row.createTime | dateformat('YYYY-MM-DD HH:mm:ss') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
              <template slot-scope="{row}">
                <el-button type="primary" size="mini" @click="handleUpdate(row)">修改</el-button>
                <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
        </div>

        <!-- 弹出框 -->
        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
          <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style=" margin-left:50px;">
            <el-form-item label="编号" prop="bookId">
              <el-input v-model="temp.bookId" />
            </el-form-item>
            <el-form-item label="名称" prop="bookName">
              <el-input v-model="temp.bookName" />
            </el-form-item>
            <el-form-item label="作者" prop="author">
              <el-input v-model="temp.author" />
            </el-form-item>
            <el-form-item label="价格" prop="price">
              <el-input v-model="temp.price" type="num" />
            </el-form-item>

          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">
              取消
            </el-button>
            <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
              提交
            </el-button>
          </div>
        </el-dialog>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getBookList, deleteBook, addBook, updateBook } from "@/api/book";
import Pagination from "@/components/Pagination";
export default {
  components: { Pagination },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        importance: undefined,
        bookName: undefined,
        type: undefined,
        sort: "+id"
      },
      sortOptions: [
        { label: "ID 升序", key: "+id" },
        { label: "ID 降序", key: "-id" }
      ],
      statusOptions: ["published", "draft", "deleted"],
      showReviewer: false,
      temp: {
        id: undefined,
        bookId: "",
        bookName: "",
        author: "",
        price: undefined
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "修改书籍",
        create: "新增书籍"
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        bookId: [{ required: true, message: "必填项", trigger: "change" }],
        bookName: [{ required: true, message: "必填项", trigger: "change" }],
        author: [{ required: true, message: "必填项", trigger: "change" }]
      },
      downloadLoading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      this.listLoading = true;
      const { data } = await getBookList(this.listQuery);
      this.list = data.records;
      this.total = data.total;
      // Just to simulate the time of the request
      setTimeout(() => {
        this.listLoading = false;
      }, 500);
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    async handleDelete(row) {
      const res = await deleteBook(row.id);
      this.$message({
        message: res.message,
        type: "success"
      });
      this.listQuery.page = 1;
      this.getList();
    },
    sortChange(data) {
      const { prop, order } = data;
      if (prop === "id") {
        this.sortByID(order);
      }
    },
    sortByID(order) {
      if (order === "ascending") {
        this.listQuery.sort = "+id";
      } else {
        this.listQuery.sort = "-id";
      }
      this.handleFilter();
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        bookId: "",
        bookName: "",
        author: "",
        price: undefined
      };
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    async updateData() {
      this.$refs["dataForm"].validate(async valid => {
        if (valid) {
          const tempData = Object.assign({}, this.temp);
          tempData.timestamp = +new Date(tempData.timestamp);
          const res = await updateBook(tempData);
          this.$message({
            message: res.message,
            type: "success"
          });
          this.listQuery.page = 1;
          this.getList();
          this.dialogFormVisible = false;
        }
      });
    },
    async createData() {
      this.$refs["dataForm"].validate(async valid => {
        if (valid) {
          const res = await addBook(this.temp);
          this.$message({
            message: res.message,
            type: "success"
          });
          this.listQuery.page = 1;
          this.getList();
          this.list.unshift(this.temp);
          this.dialogFormVisible = false;
        }
      });
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}`
        ? "ascending"
        : sort === `-${key}`
        ? "descending"
        : "";
    }
  }
};
</script>

<style>
</style>
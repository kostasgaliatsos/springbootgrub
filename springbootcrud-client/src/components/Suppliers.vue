<template>
  <div class='wrapper'>
    <div class='animated fadeIn'>
      <div class='row'>
        <div class='col-lg-12'>
          <el-card>
            <div slot='header'>
              <i class='icon-building'></i> Προμηθευτές
            </div>
            <el-row>
              <el-col :span='5'>
                <el-input
                  v-model='query'
                  placeholder='Search'
                  autoComplete='companyName'>
                </el-input>
              </el-col>
              <el-col :span='0.2'><div class='grid-content bg-purple'>&nbsp;</div></el-col>
              <el-col :span='12'>
                <el-button
                  class='el-button--primary'
                  @click='refreshSuppliers()'>Search</el-button>
                  <!-- kostas: added clear button -->
                  <el-button
                  class='el-button--danger'
                  @click='clear()'>Clear</el-button>
              </el-col>
            </el-row>
            <el-table
              :data='suppliers'
              stripe
              style='width: 100%'
              @selection-change='onSupplierSelected'>
              <el-table-column
                v-for='(field, index) in fields'
                v-bind:key='index'
                :prop='field.name'
                :label='field.title'
                :sortable='true'>
              </el-table-column>
              <el-table-column label='Operations' width='100'>
                <template slot-scope='scope'>
                  <el-button
                    size='mini'
                    class='el-button--warning'
                    @click='onSupplierSelected(scope.row)'
                    >Edit</el-button>
                </template>
              </el-table-column>
            </el-table>

            <div style='text-align: right; margin-top: 1em'>
              <el-button
                class='el-button--success'
                @click='createSupplier'>
                <i class='fa fa-plus'></i>
                Δημιουργία
              </el-button>
            </div>
          </el-card>
        </div>
      </div>
    </div>
    <supplier></supplier>
  </div>
</template>
<script>
import Supplier from '@/components/Supplier'

export default {
  components: {
    Supplier
  },
  created () {
    this.refreshSuppliers()
    console.log('Suppliers created')
  },
  mounted () {
    // subscribe to the 'row-selected' event (wherever it may come from, should come from the child table component)
    this.$events.$on('row-selected', eventData => this.onSupplierSelected(eventData))
    this.$events.$on('supplier-edited', eventData => this.onSupplierEdited(eventData))
    console.log('Suppliers mounted')
  },
  beforeDestroy () {
    // unsubscibe from events
    this.$events.$off('row-selected')
    this.$events.$off('supplier-edited')
  },
  destroyed () {
    console.log('Suppliers destroyed')
  },
  data: function () {
    return {
      url: 'suppliers/search/findByQuery?query=',
      query: '',
      suppliers: [],
      fields: [
        {
          name: 'id',
          title: 'Α/Α',
          sortField: 'id'
        },
        {
          name: 'companyName',
          title: 'Εταιρία',
          sortField: 'companyName'
        },
        {
          name: 'vatNumber',
          title: 'ΑΦΜ',
          sortField: 'vatNumber'
        }
      ]
    }
  },
  watched: {
    query: function (newValue) {
      this.query = newValue
      console.log(newValue)
      this.refreshSuppliers()
    }
  },
  methods: {
    createSupplier (event) {
      console.log('fire edit-supplier event')
      this.$events.fire('edit-supplier', null)
    },
    onSupplierSelected (dataItem) {
      console.log('fire edit-supplier event')
      this.$events.fire('edit-supplier', dataItem)
    },
    onSupplierEdited (dataItem) {
      this.refreshSuppliers()
    },
    refreshSuppliers () {
      this.$http.get(this.url + this.query)
        .then(response => {
          this.suppliers = response.data._embedded.suppliers
        })
        .catch(e => {
          console.log('error: ')
          console.log(e)
        })
    },
    clear () {
      this.query = ''
      this.refreshSuppliers()
    }
  }
}

</script>

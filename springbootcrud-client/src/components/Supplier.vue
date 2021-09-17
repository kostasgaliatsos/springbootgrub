<template>
  <el-dialog
    type='warning' title='Διαχείριση Χρήστη' :visible='visible' :modal='true'
             :close-on-click-modal='false'
             :close-on-press-escape='false' :modal-append-to-body='false' :show-close='false' width='75%'
             @close='clearValidation'>
    <el-card>
      <el-form ref='supplierForm' :model='supplier' :rules='rules' label-position='top'>

        <el-row :gutter='20'>

          <!-- right column
               mandatory fields first -->
          <el-col :span='12'>

            <el-form-item label='Εισάγετε το όνομα της εταιρίας'
                        prop='companyName'>
            <el-input prefix-icon='fa fa-building'
                      v-model='supplier.companyName'
                      placeholder='Εταιρία προμηθευτή'
                      autoComplete='companyName'>
            </el-input>

            <el-form-item label='Εισάγετε το ΑΦΜ'
                        prop='vatNumber'>
            <el-input prefix-icon='fa fa-building'
                      v-model='supplier.vatNumber'
                      placeholder='ΑΦΜ εταιρίας'
                      autoComplete='vatNumber'>
            </el-input>
          </el-form-item>

          <el-form-item label='Εισάγετε την ΔΟΥ'
                        prop='irsOffice'>
            <el-input prefix-icon='fa fa-building'
                      v-model='supplier.irsOffice'
                      placeholder='ΔΟΥ προμηθευτή'
                      autoComplete='irsOffice'>
            </el-input>
          </el-form-item>

            <el-form-item label='Εισάγετε το όνομα του προμηθευτή'
                        prop='firstName'>
            <el-input prefix-icon='fa fa-user'
                      v-model='supplier.firstName'
                      placeholder='Όνομα προμηθευτή'
                      autoComplete='firstName'>
            </el-input>
            </el-form-item>

            <el-form-item label='Εισάγετε το επώνυμο του προμηθευτή'
                        prop='lastName'>
            <el-input prefix-icon='fa fa-user'
                      v-model='supplier.lastName'
                      placeholder='Επώνυμο προμηθευτή'
                      autoComplete='lastName'>
            </el-input>
            </el-form-item>

          </el-form-item>
        </el-col>
        <el-col :span='12'>

        </el-col>
        <el-col :span='12'>
          <el-form-item label='Εισάγετε διεύθυνση'
                        prop='address'>
            <el-input prefix-icon='fa fa-building'
                      v-model='supplier.address'
                      placeholder='Διεύθυνση εταιρίας'
                      autoComplete='address'>
            </el-input>
          </el-form-item>

           <el-form-item label='Εισάγετε τον ΤΚ'
                        prop='zipCode'>
            <el-input prefix-icon='fa fa-building'
                      v-model='supplier.zipCode'
                      placeholder='TK εταιρίας'
                      autoComplete='zipCode'>
            </el-input>
          </el-form-item>

          <el-form-item label='Εισάγετε την πόλη'
                        prop='city'>
            <el-input prefix-icon='fa fa-building'
                      v-model='supplier.city'
                      placeholder='Πόλη εταιρίας'
                      autoComplete='city'>
            </el-input>
          </el-form-item>

          <el-form-item label='Εισάγετε την χώρα'
                        prop='country'>
            <el-input prefix-icon='fa fa-building'
                      v-model='supplier.country'
                      placeholder='Χώρα εταιρίας'
                      autoComplete='country'>
            </el-input>
          </el-form-item>

          </el-col>
        </el-row>
      </el-form>

      <el-row>
        <el-col style='text-align: right'>
          <el-button type='success'
                     icon='fa fa-save'
                     @click='save'> Αποθήκευση </el-button>

          <el-button type='danger'
                     icon='fa fa-remove'
                     @click='confirmDelete'
                     :disabled='!isDeletable'> Διαγραφή </el-button>
        </el-col>
      </el-row>
    </el-card>

    <div slot='footer' class='dialog-footer card-footer'>
      <el-button type='warning'
                 icon='el-icon-back'
                 @click='cancel'> Επιστροφή </el-button>
    </div>

  </el-dialog>
</template>
<script>
import constants from '@/components/constants'

export default {
  name: 'supplier',
  components: {},
  data: function () {
    return {
      visible: false,
      context: 'Supplier',
      supplier: initSupplier(),
      rules: {
        firstName: {
          required: false,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        },
        lastName: {
          required: false,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        },
        companyName: {
          required: true,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        },
        vatNumber: {
          required: true,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        },
        irsOffice: {
          required: false,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'

        },
        address: {
          required: false,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        },
        zipcode: {
          required: false,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        },
        city: {
          required: false,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        },
        country: {
          required: false,
          max: constants.sizes.SIZE_M,
          trigger: 'blur'
        }
      }
    }
  },
  created () {
    console.log('Supplier created')
  },
  mounted () {
    this.$events.$on('edit-supplier', eventData => this.onEditSupplier(eventData))
    console.log('Supplier mounted')
  },
  destroyed () {
    this.$events.$off('edit-supplier')
    console.log('Supplier destroyed')
  },
  computed: {
    isDeletable: function () {
      return this.supplier.id != null
    }
  },
  methods: {
    onEditSupplier (eventData) {
      console.log('Edit supplier: ' + eventData)
      if (eventData != null) {
        this.$http.get('/suppliers/' + eventData.id)
          .then(response => {
            this.supplier = response.data
            this.visible = true
            this.clearValidation()
          })
      } else {
        Object.assign(this.$data.supplier, initSupplier())
        this.visible = true
        this.clearValidation()
      }
    },
    save () {
      this.$refs['supplierForm'].validate().then(() => {
        let _self = this
        if (this.supplier.id != null) {
          // existing supplier, update
          this.$http.patch('suppliers/' + this.supplier.id, this.supplier, {
            // transform the selected roles into URIs, before sending
            transformRequest: [function (data, headers) {
              return _self.transformRequest(data)
            }]
          }).then(response => {
            this.handleSuccess(response)
          }).catch(e => this.handleError(e))
        } else {
          // new supplier, create
          this.$http.post('suppliers', this.supplier, {
            // transform the selected roles into URIs, before sending
            transformRequest: [function (data, headers) {
              return _self.transformRequest(data)
            }]
          }).then(response => this.handleSuccess(response))
            .catch(e => this.handleError(e))
        }
      }).catch(e => { console.error('validation FAILED') })
    },
    cancel () {
      this.visible = false
      this.clearValidation()
    },
    handleSuccess (response) {
      this.visible = false
      this.successFloat(this.$messages.successAction)
      console.log('fire supplier-edited event')
      this.$events.fire('supplier-edited', this.supplier)
    },
    handleError (e) {
      this.showDefaultError(e)
    },
    confirmDelete () {
      this.$confirm(this.$messages.confirmAction, this.$messages.confirmActionTitle, {
        confirmButtonText: this.$messages.yes,
        cancelButtonText: this.$messages.no,
        cancelButtonClass: 'btn btn-warning',
        confirmButtonClass: 'btn btn-danger',
        closeOnClickModal: false,
        closeOnPressEscape: false,
        type: 'warning'
      }).then(() => {
        // delete supplier
        this.$http.delete('suppliers/', this.supplier.id)
          .then(response => this.handleSuccess(response))
      })
    },
    transformRequest (data) {
      return JSON.stringify(data)
    },
    clearValidation () {
      if (this.$refs['supplierForm']) {
        this.$refs['supplierForm'].clearValidate()
      }
    }
  }
}

/**
* Create a new empty Supplier
* @returns {{id:null, firstName:string, lastName:string, companyName:string, vatNumber:string,
* irsOffice:string, address:string, zipcode:string, city:string, country:string}}
*/
function initSupplier () {
  return {
    id: null,
    firstName: '',
    lastName: '',
    companyName: '',
    vatNumber: '',
    irsOffice: '',
    address: '',
    zipcode: '',
    city: '',
    country: ''
  }
}

</script>

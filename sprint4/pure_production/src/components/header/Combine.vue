
<template>
  <Modal
    v-model="showModal" draggable sticky :reset-drag-position="true" :mask="false" :width="600" title="fusion"
    ok-text="save" cancel-text="cancel" @on-visible-change="handleVisibleChange" @on-ok="saveReplacedImg"
    @on-cancel="closeModal"
  >
    <Upload
      ref="upload" action="#" :multiple="false" :show-upload-list="false" :before-upload="loadImg"
      :format="['jpg', 'jpeg', 'png']" :accept="accept" :on-format-error="handleFormatError"
    >
      <div class="upload">
        <Icon type="ios-add-circle-outline" />
        select other
      </div>
    </Upload>
    <div style="padding:20px">
      <canvas id="c" ref="c" class="right" width="600" height="600" />
    </div>
  </Modal>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import { fabric } from 'fabric'

export default {
  name: 'Combine',
  model: {
    prop: 'combineModalVal',
    event: 'input',
  },
  props: ['combineModalVal'],
  data() {
    return {
      accept: 'image/jpeg,image/jpg,image/png',
      showModal: false,
      __canvas: null,
      a: 0,
    }
  },
  watch: {
    combineModalVal(nv) {
      this.showModal = nv
      if (nv) {
        this.__canvas.clear()
        fabric.Image.fromURL(this.getCurrentImgSrc, (img, isError) => {
          const width = img.height
          img.set({ originX: 'left', originY: 'top' }).scale(600 / width)
          this.__canvas.add(img)
        })
      }
    },
    currentMainPreviewImgName() {

    },

  },
  mounted() {
    this.showModal = this.combineModalVal
    const canvas = this.__canvas = new fabric.Canvas('c') // cavans
    // 背景
    // fabric.Image.fromURL(this.getCurrentImgSrc, (img, isError) => {
    //     const width = img.height;
    //     img.set({ originX: 'left', originY: 'top' }).scale(600 / width);
    //     canvas.add(img);
    // });
  },
  computed: {
    ...mapState(['currentMainPreviewImgName']),
    ...mapGetters(['getCurrentImgSrc']),
  },
  methods: {

    loadImg(file) {
      const reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = (img) => {
        // 将上传的图片名字和base64Url分发
        fabric.Image.fromURL(img.target.result, (img, isError) => {
          img.set({ originX: 'left', originY: 'top' }).scale(600 / img.height)
          this.__canvas.add(img)
        })
        // 清空上传列表
        this.$refs.upload.clearFiles()
      }
      // 禁止默认自动上传
      return false
    },
    handleFormatError() {
      console.log('This file is not image')
    },
    handleVisibleChange(val) {
      val || this.$emit('input', val)
    },

    saveReplacedImg() {
      this.exportA()
    },
    closeModal() {

    },
    exportA() {
      const canvas = this.__canvas
      const dataURL = canvas.toDataURL({
        format: 'png',
        left: 0,
        top: 0,
        width: 600,
        height: 600,
      })

      this.$store.dispatch('updateImgData', {
        imgName: this.currentMainPreviewImgName,
        imgSrc: dataURL,
      })
      console.log(dataURL)
    },

  },
}
</script>

<style lang="scss" scoped>
.row {
    display: flex;

    .col {
        height: 100%;
        height: 300px;

        .right {
            height: 300px;
        }
    }
}

.background-replace {
    display: flex;

    .left {
        width: 200px;
        height: 280px;
    }

    .middle {
        width: 200px;
        height: 280px;
    }

    .right {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 200px;
        height: 280px;

        Button {
            margin: 15px 0;
        }
    }
}
</style>

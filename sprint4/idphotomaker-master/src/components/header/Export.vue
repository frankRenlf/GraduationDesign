
<template>
  <Modal
    v-model="showModal" draggable sticky :reset-drag-position="true" :mask="false" :width="800" title="export"
    ok-text="save" cancel-text="cancel" @on-visible-change="handleVisibleChange" @on-ok="saveReplacedImg"
    @on-cancel="closeModal"
  >
    <div style="position: relative;height: 400px;">
      <Row :gutter="5">
        <Col :span="12">
          <img :src="src" alt="" width="300" height="380">
        </Col>
        <Col :span="12">
          <RadioGroup v-model="border" @on-change="onChange">
            <Radio label="one inch" border />
            <Radio label="two inches" border />
            <Radio label="smaller" border />
            <Radio label="bigger" border />
          </RadioGroup>
          <canvas id="abb" ref="ab" width="200" height="280" style="display: none;" />
        </Col>
      </Row>
    </div>
  </Modal>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
export default {
  name: 'Export',
  model: {
    prop: 'exportModalVal',
    event: 'input',
  },
  props: ['exportModalVal'],
  data() {
    return {
      accept: 'image/jpeg,image/jpg,image/png',
      showModal: false,
      border: 'one inch',
      src: '',

    }
  },
  watch: {
    exportModalVal(nv) {
      this.showModal = nv

      if (nv)
        this.src = this.getCurrentImgSrc
    },
    currentMainPreviewImgName() {

    },

  },
  beforeMount() {
    this.showModal = this.exportModalVal
  },
  mounted() {
    this.src = this.getCurrentImgSrc
  },
  computed: {
    ...mapState(['currentMainPreviewImgName']),
    ...mapGetters(['getCurrentImgSrc']),
  },
  methods: {

    onChange() {

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
      let w = 200
      let h = 280
      switch (this.border) {
        case 'one inch':
          w = 295
          h = 413
          break
        case 'two inches':
          w = 413
          h = 625
          break
        case 'smaller':
          w = 389
          h = 466
          break
        case 'bigger':
          w = 413
          h = 531
          break
      }
      $('#abb').attr({
        width: w,
        height: h,
      }).css({

        width: w,
        height: h,
      })
      const frontImg = new Image()
      frontImg.src = this.getCurrentImgSrc
      frontImg.width = w
      frontImg.height = h
      frontImg.onload = () => {
        const canvas = this.$refs.ab
        const ctx = canvas.getContext('2d')
        // ctx.fillStyle = bgColor
        ctx.fillRect(0, 0, w, h)
        ctx.drawImage(frontImg, 0, 0, w, h)
        const currentImgSrc = canvas.toDataURL('image/png')
        if (currentImgSrc) {
          const a = document.createElement('a')
          const event = new MouseEvent('click')
          a.download = '导出.png'
          a.href = currentImgSrc
          a.dispatchEvent(event)
        }
        else {
          this.$Message.error('no image selected')
        }
      }
    },

  },
}
</script>

<style lang="scss" scoped>
#imgCutShow {
    position: absolute;
    display: none;
    border: 1px dashed #c89191;
    margin: 0px 410px;
}

.canvasDiv {
    position: relative;
    border: 1px dashed #c89191;
}

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

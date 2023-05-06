
<template>
  <Modal
    v-model="showModal" draggable sticky :reset-drag-position="true" :mask="false" :width="600" title="modify"
    ok-text="save" cancel-text="cancel" @on-visible-change="handleVisibleChange" @on-ok="saveReplacedImg"
    @on-cancel="closeModal"
  >
    <div>
      <Row :gutter="10">
        <Col :span="12">
          <canvas id="d" ref="d" class="right" width="280" height="400" />
        </Col>

        <Col :span="12">
          <div>brightness</div>
          <Slider v-model="brightness" :min="-100" :max="100" :disabled="!obj" @on-change="brightnessChange" />
          <div>blur</div>
          <Slider v-model="blur" :min="0" :max="100" :disabled="!obj" @on-change="blurChange" />
          <div>contrast</div>
          <Slider v-model="contrast" :min="-100" :max="100" :disabled="!obj" @on-change="contrastChange" />
          <div>noise</div>
          <Slider v-model="noise" :min="0" :max="1000" :disabled="!obj" @on-change="noiseChange" />
          <div>hue</div>
          <Slider v-model="hue" :min="-100" :max="100" :disabled="!obj" @on-change="hueChange" />
        </Col>
      </Row>
    </div>
  </Modal>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import { fabric } from 'fabric'

import { Slider } from 'view-design'
export default {
  name: 'Combine',
  model: {
    prop: 'processModalVal',
    event: 'input',
  },
  props: ['processModalVal'],
  data() {
    return {
      showModal: false,
      __canvas: null,
      brightness: 0,
      blur: 0,
      contrast: 0,
      noise: 0,
      hue: 0,
      obj: undefined,
    }
  },
  watch: {
    processModalVal(nv) {
      this.showModal = nv
      if (nv) {
        this.$nextTick(() => {
          const canvas = this.__canvas
          this.__canvas.clear()
          fabric.Image.fromURL(this.getCurrentImgSrc, (img, isError) => {
            const width = img.width

            img.set({ originX: 'left', originY: 'top' }).scale(280 / width)
            canvas.add(img)
          })
        })
      }
    },
    currentMainPreviewImgName() {
    },
  },
  mounted() {
    this.showModal = this.processModalVal
    const canvas = this.__canvas = new fabric.Canvas('d') // cavans
    canvas.on({
      'selection:created': () => {
        this.obj = canvas.getActiveObject()
        this.applyFilter()
      },
      'selection:cleared': () => {
        this.obj = undefined
      },
    })
  },
  computed: {
    ...mapState(['currentMainPreviewImgName']),
    ...mapGetters(['getCurrentImgSrc']),

  },
  methods: {

    brightnessChange() {
      this.applyFilterValue(1, 'brightness', this.brightness / 100)
    },

    blurChange() {
      this.applyFilterValue(0, 'blur', this.blur / 100)
    },

    contrastChange() {
      this.applyFilterValue(2, 'contrast', this.contrast / 100)
    },
    noiseChange() {
      this.applyFilterValue(3, 'noise', this.noise)
    },

    hueChange() {
      this.applyFilterValue(4, 'rotation', this.hue / 100)
    },
    applyFilter() {
      const canvas = this.__canvas
      const object = canvas.getActiveObject()
      object.filters[0] = new fabric.Image.filters.Blur({
        blur: 0,
      })
      object.filters[1] = new fabric.Image.filters.Brightness({
        brightness: 0,
      })
      object.filters[2] = new fabric.Image.filters.Contrast({
        contrast: 0,
      })
      object.filters[3] = new fabric.Image.filters.Noise({
        noise: 0,
      })
      object.filters[4] = new fabric.Image.filters.HueRotation({
        rotation: 0,
      })
    },

    applyFilterValue(index, prop, value) {
      console.log(value)
      const canvas = this.__canvas
      const object = canvas.getActiveObject()
      object.filters[index][prop] = value
      object.applyFilters()
      canvas.renderAll()
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
  components: { Slider },
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

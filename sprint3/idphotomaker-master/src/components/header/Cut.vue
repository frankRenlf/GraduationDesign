
<template>
    <Modal v-model="showModal" draggable sticky :reset-drag-position="true" :mask="false" :width="600" title="钢笔抠图"
        ok-text="保存修改" cancel-text="取消修改" @on-visible-change="handleVisibleChange" @on-ok="saveReplacedImg"
        @on-cancel="closeModal">
        <div style="position: relative;height: 400px;">
            <div class="canvasDiv" id="drawPanel" style="height:400px">
            </div>
            <!-- <canvas id="a" style="width: 400px;height:400px;"></canvas> -->
        </div>

    </Modal>
</template>
  
<script>
import { mapGetters, mapState } from 'vuex'
import penCutout from "./penCutout"
export default {
    name: 'combine',
    model: {
        prop: 'combineModalVal',
        event: 'input',
    },
    props: ['combineModalVal'],
    data() {
        return {
            accept: 'image/jpeg,image/jpg,image/png',
            showModal: false,

            a: 0,
            penCutout: undefined
        }
    },
    watch: {
        combineModalVal(nv) {
            this.showModal = nv


        },
        currentMainPreviewImgName() {

        },

    },
    beforeMount() {
        this.showModal = this.combineModalVal;
     
    },
    mounted() {
        this.penCutout = new penCutout();
        this.penCutout.init({
            drawPanel: "drawPanel",
            imgSrc: this.getCurrentImgSrc,
            canvasId:'a',
            penColor: "#ff40ef",
            imgBackSrc: '../../../static/tranback.png',
            width: 400,
            height: 400
        });
      




        //事件
        // $("#btnPoints").click(function () {
        //     alert(JSON.stringify(_penCutout.can.pointList));
        // })
        // $("#btnCut").click(function () {
        //     _penCutout.createCutImg(function (imgSrcData, w, h) {
        //         $("#imgCutShow").attr("src", imgSrcData).css({ "display": "block", "width": w, "height": h });
        //     })
        // })
        // $("#btnDown").click(function () {
        //     _penCutout.downLoad();
        // })
        // $("#redo").click(function () {
        //     _penCutout.ReDo();
        // })

    },
    computed: {
        ...mapState(['currentMainPreviewImgName']),
        ...mapGetters(['getCurrentImgSrc']),
    },
    methods: {

        koutu() {
            this.penCutout.eventBind();
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
           this.penCutout.createCutImg((dataURL)=>{
            this.$store.dispatch('updateImgData', {
                imgName: this.currentMainPreviewImgName,
                imgSrc: dataURL,
            })
            console.log(dataURL)
           })

           
        }

    }
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
  
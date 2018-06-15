using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

//button 1: open source file
//button 2: analyze the file
//textbox10: show source file path
//button 3: save the result
//textbox1:number of records
//textbox2:min of entry time
//textbox3:max of entry time
//textbox4:avg of entry time
//textbox5:min of inter. time
//textbox6:max of inter. time
//textbox7:avg of inter. time
//textbox8:total time
//textbox9:backspace count

namespace RebateEntryEvaluator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            OpenFileDialog openf = new OpenFileDialog();
            openf.Filter = "Text File | *.txt";
            openf.ShowDialog();
            textBox10.Text = openf.FileName;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if(string.IsNullOrWhiteSpace(textBox10.Text))
                MessageBox.Show("Please choose a text file first. :)");
            else
            {          
            //number of records
            int lineCount = File.ReadLines(textBox10.Text).Count();
            textBox1.Text = lineCount.ToString();

            //read the source file
            StreamReader sr = new StreamReader(textBox10.Text);
            
            //pick and store infos from source file into arrays
            string[] startstring = new string[lineCount];
            string[] endstring = new string[lineCount];
            string[] backspace = new string[lineCount];
            int i = 0;
            while (sr.Peek() > -1)
            {
                string s = sr.ReadLine();
                string[] element = s.Split('\t');
                startstring[i] = element[12];
                endstring[i] = element[13];
                backspace[i] = element[14];
                i++;                
            }           
            sr.Close();
 
            //convert string to DateTime
            DateTime[] start = Array.ConvertAll(startstring, DateTime.Parse);
            DateTime[] end = Array.ConvertAll(endstring, DateTime.Parse);
            //convert string to int
            int[] backcount = Array.ConvertAll(backspace, int.Parse);

            //define arrays of time differences, and the sum,max,min
            //digital suffix 1 refers to entry time, 2 refers to interval time
            TimeSpan[] difference = new TimeSpan[lineCount];
            TimeSpan sum1 = TimeSpan.Zero;
            TimeSpan max1 = TimeSpan.MinValue;
            TimeSpan min1 = TimeSpan.MaxValue;
            TimeSpan avg1 = TimeSpan.Zero;
            TimeSpan[] interval = new TimeSpan[lineCount - 1];
            TimeSpan sum2 = TimeSpan.Zero;
            TimeSpan max2 = TimeSpan.MinValue;
            TimeSpan min2 = TimeSpan.MaxValue;
            TimeSpan avg2 = TimeSpan.Zero;
            int backcountsum = 0;

            //calculate values
            for (int j=0; j<lineCount; j++)
            {
                difference[j] = end[j] - start[j];
                sum1 = sum1 + difference[j];
                if(max1 < difference[j])
                {
                    max1 = difference[j];
                } 
                if(min1 > difference[j])
                {
                    min1 = difference[j];
                }
                backcountsum = backcountsum + backcount[j];            // backspace count
            }
                        
            for(int j=0; j<(lineCount-1); j++)
            {
                interval[j] = start[j + 1] - end[j];
                sum2 = sum2 + interval[j];
                if (max2 < interval[j])
                {
                    max2 = interval[j];
                }
                if (min2 > interval[j])
                {
                    min2 = interval[j];
                }
            }

            //average and total time
            avg1 = new TimeSpan(sum1.Ticks / lineCount);
            
            if(lineCount == 1)
                {
                   max2 = TimeSpan.Zero;
                   min2 = TimeSpan.Zero;
                 }

            else
                {
                   avg2 = new TimeSpan(sum2.Ticks/(lineCount-1));
                }

            TimeSpan total = end[lineCount - 1] - start[0];

            //show values in textboxes on the interface
            textBox2.Text = min1.ToString(@"mm\:ss");
            textBox3.Text = max1.ToString(@"mm\:ss");
            textBox4.Text = avg1.ToString(@"mm\:ss");
            textBox5.Text = min2.ToString(@"mm\:ss");
            textBox6.Text = max2.ToString(@"mm\:ss");
            textBox7.Text = avg2.ToString(@"mm\:ss");
            textBox8.Text = total.ToString(@"mm\:ss");
            textBox9.Text = backcountsum.ToString();
            }
        }
      
        private void button3_Click(object sender, EventArgs e)
        {
            SaveFileDialog savef = new SaveFileDialog();
            savef.FileName = "DefaulFileName.txt";
            savef.Filter = "Text File | *.txt";
            if (savef.ShowDialog() == DialogResult.OK)
            {
                StreamWriter sr = new StreamWriter(savef.OpenFile());
                sr.WriteLine("Number of records: " + textBox1.Text);
                sr.WriteLine("Minimum entry time: " + textBox2.Text);
                sr.WriteLine("Maximum entry time: " + textBox3.Text);
                sr.WriteLine("Average entry time: " + textBox4.Text);
                sr.WriteLine("Minimum inter-record time: " + textBox5.Text);
                sr.WriteLine("Maximum inter-record time: " + textBox6.Text);
                sr.WriteLine("Average inter-record time: " + textBox7.Text);
                sr.WriteLine("Total time: " + textBox8.Text);
                sr.WriteLine("Backspace count: " + textBox9.Text);
                sr.Close();
            }

        }
    }
}
